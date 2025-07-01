import {Component, ViewChild} from '@angular/core';
import {Customer} from "../../../entity/customer";
import {CustomerService} from "../../../service/customerservice";
import {MatPaginator} from "@angular/material/paginator";
import {MatTableDataSource} from "@angular/material/table";
import {FormBuilder, FormControl, FormGroup, ValidationErrors, Validators} from "@angular/forms";
import {UiAssist} from "../../../util/ui/ui.assist";
import {Gender} from "../../../entity/gender";
import {Designation} from "../../../entity/designation";
import {GenderService} from "../../../service/genderservice";
import {MatDialog} from "@angular/material/dialog";
import {MessageComponent} from "../../../util/dialog/message/message.component";
import {ConfirmComponent} from "../../../util/dialog/confirm/confirm.component";
import {Customerstatus} from "../../../entity/customerstatus";
import {RegexService} from "../../../service/regexservice";
import {DatePipe} from "@angular/common";
import {AuthorizationManager} from "../../../service/authorizationmanager";
import {Customertype} from "../../../entity/customertype";
import {Cusstatusservice} from "../../../service/cusstatusservice";
import {Custypeservice} from "../../../service/custypeservice";
import {NumberService} from "../../../service/numberservice";


@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.css']
})

export class CustomerComponent {
  // Table column keys, headers, and data bindings
  columns: string[] = ['code', 'fullname', 'gender', 'mobile', 'email', 'address'];
  headers: string[] = ['Code', 'Full Name', 'Gender', 'Mobile', 'Email', 'Address'];
  binders: string[] = ['code', 'fullname', 'gender.name', 'mobile', 'email', 'address'];

  // cscolumns: string[] = ['cscode', 'cscallingname', 'csgender', 'csdesignation', 'csname', 'csmodi'];
  cscolumns: string[] = ['cscode', 'csfullname', 'csgender', 'csmobile', 'csemail','csaddress'];
  csprompts: string[] = ['Search by Code', 'Search by Full Name', 'Search by Gender', 'Search by Mobile', 'Search by Email', 'Search by Address'];

  // Form groups for searching and customer entry
  public csearch!: FormGroup;
  public ssearch!: FormGroup;
  public form!: FormGroup;

  customer!: Customer;
  oldcustomer!: Customer;

  selectedrow: any;

  customers: Array<Customer> = [];
  data!: MatTableDataSource<Customer>;
  imageurl: string = '';
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  imageempurl: string = 'assets/default.png'

  // Button enable flags
  enaadd: boolean = false;
  enaupd: boolean = false;
  enadel: boolean = false;

  hasInsertAuthority: boolean = false;
  hasUpdateAuthority: boolean = false;
  hasDeleteAuthority: boolean = false;

  // Dropdown data
  genders: Array<Gender> = [];
  customerstatuses: Array<Customerstatus> = [];
  customertypes: Array<Customertype> = [];

  // Regex pattern holder
  regexes: any;

  uiassist: UiAssist;

  //mindate!:Date;

  constructor(
    private es: CustomerService,
    private gs: GenderService,
    private cs: Cusstatusservice,
    private ct: Custypeservice,
    private rs: RegexService,
    private fb: FormBuilder,
    private dg: MatDialog,
    private dp: DatePipe,
    private ns: NumberService,
    public authService:AuthorizationManager) {

    // Initialize UI helper class
    this.uiassist = new UiAssist(this);

    // Create customer search form
    this.csearch = this.fb.group({
      "cscode": new FormControl(),
      "csfullname": new FormControl(),
      "csgender": new FormControl(),
      "csmobile": new FormControl(),
      "csemail": new FormControl(),
      "csaddress": new FormControl(),
    });

    // Create short search filters
    this.ssearch = this.fb.group({
      "sscode": new FormControl(),
      "ssfullname": new FormControl(),
      "ssmobile": new FormControl()
    });

    // Create customer form with basic validations
    this.form = this.fb.group({
      "code": new FormControl('', [Validators.required]),
      "fullname": new FormControl('', [Validators.required]),
      "callingname": new FormControl('', [Validators.required]),
      "gender": new FormControl('', [Validators.required]),
      // "nic": new FormControl('', [Validators.required]),
      // "photo": new FormControl('', [Validators.required]),
      "mobile": new FormControl('', [Validators.required]),
      "email": new FormControl('', [Validators.required]),
      "address": new FormControl('', [Validators.required]),
      // "doassignment": new FormControl('', [Validators.required]),
      "customertype": new FormControl('', [Validators.required]),
      "customerstatus": new FormControl('', [Validators.required]),
    }, {updateOn: 'change'});


  }

  // Initialize customer form
  ngOnInit() {
    this.initialize();
    this.enableButtons(true, false, false);
  }

  initialize() {

    const authoritiesArray = this.authService.getAuthorities();
    if (authoritiesArray !== undefined && Array.isArray(authoritiesArray)) {
      const authorities = this.authService.extractAuthorities(authoritiesArray);
      this.buttonStates(authorities);
    }

    this.createView();

    // Fetch dropdown values for Gender
    this.gs.getAllList().then((gens: Gender[]) => {
      this.genders = gens;
    });

    // Fetch dropdown values for Customer status
    this.cs.getAllList().then((stes: Customerstatus[]) => {
      this.customerstatuses = stes;
    });

    // Fetch dropdown values for Customer type
    this.ct.getAllList().then((types: Customertype[]) => {
      this.customertypes = types;
    });

    // Load regex validations
    this.rs.get('customers').then((regs: []) => {
      this.regexes = regs;
      this.createForm();
    });

  }

  // Determine button access based on user roles
  buttonStates(authorities: { module: string; operation: string }[]): void {
    this.hasInsertAuthority = authorities.some(authority => authority.module === 'customer' && authority.operation === 'insert');
    this.hasUpdateAuthority = authorities.some(authority => authority.module === 'customer' && authority.operation === 'update');
    this.hasDeleteAuthority = authorities.some(authority => authority.module === 'customer' && authority.operation === 'delete');

  }

  createView() {
    this.imageurl = 'assets/pending.gif';
    this.loadTable("");
  }

// Assign regex validations dynamically and detect field changes
  createForm() {
    this.form.controls['code'].setValidators([Validators.required]);
    this.form.controls['fullname'].setValidators([Validators.required, Validators.pattern(this.regexes['fullname']['regex'])]);
    this.form.controls['callingname'].setValidators([Validators.required, Validators.pattern(this.regexes['callingname']['regex'])]);
    this.form.controls['gender'].setValidators([Validators.required]);
    // this.form.controls['photo'].setValidators([Validators.required]);
    this.form.controls['mobile'].setValidators([Validators.required, Validators.pattern(this.regexes['mobile']['regex'])]);
    this.form.controls['email'].setValidators([Validators.required,Validators.pattern(this.regexes['email']['regex'])]);
    this.form.controls['address'].setValidators([Validators.required, Validators.pattern(this.regexes['address']['regex'])]);
    // this.form.controls['doassignment'].setValidators([Validators.required]);
    this.form.controls['customertype'].setValidators([Validators.required]);
    this.form.controls['customerstatus'].setValidators([Validators.required]);

    Object.values(this.form.controls).forEach( control => { control.markAsTouched(); } );

    for (const controlName in this.form.controls) {
      const control = this.form.controls[controlName];
      control.valueChanges.subscribe(value => {
            // @ts-ignore
            if (controlName == "dobirth" || controlName == "doassignment")
                value = this.dp.transform(new Date(value), 'yyyy-MM-dd');

            if (this.oldcustomer != undefined && control.valid) {
              // @ts-ignore
              if (value === this.customer[controlName]) {
                control.markAsPristine();
              } else {
                control.markAsDirty();
              }
            } else {
              control.markAsPristine();
            }
          }
      );

      }

    this.enableButtons(true,false,false);

  }

  // Enable or disable Add, Update, and Delete buttons
  enableButtons(add:boolean, upd:boolean, del:boolean){
    this.enaadd=add;
    this.enaupd=upd;
    this.enadel=del;
  }

  // Fetch customer records and load into the table
  loadTable(query: string) {
    this.es.getAll(query)
      .then((cuss: Customer[]) => {
        this.customers = cuss;
        this.imageurl = 'assets/fullfilled.png';
        this.ns.setLastSequenceNumber(this.customers[this.customers.length-1].code);
        this.generateNumber();
      })
      .catch((error) => {
        console.log(error);
        this.imageurl = 'assets/rejected.png';
      })
      .finally(() => {
        this.data = new MatTableDataSource(this.customers);
        this.data.paginator = this.paginator;
      });

  }

  getModi(element: Customer) {
    return element.code + '(' + element.callingname + ')';
  }


  filterTable(): void {

    const cserchdata = this.csearch.getRawValue();

    this.data.filterPredicate = (customer: Customer, filter: string) => {
      return (cserchdata.cscode == null || customer.code.includes(cserchdata.cscode)) &&
        (cserchdata.csfullname == null || customer.fullname.toLowerCase().includes(cserchdata.csfullname)) &&
        (cserchdata.csgender == null || customer.gender.name.toLowerCase().includes(cserchdata.csgender)) &&
        (cserchdata.csmobile == null || customer.mobile.toLowerCase().includes(cserchdata.csmobile)) &&
        (cserchdata.csemail == null || customer.email.includes(cserchdata.csemail)) &&
        (cserchdata.csaddress == null || customer.address.toLowerCase().includes(cserchdata.csaddress)) ;
    };

    this.data.filter = 'xx';

  }

  // Generate new customer code
  generateNumber(): void{
    const newNumber : string = this.ns.generateNumber('C');
    this.form.controls['code'].setValue(newNumber);
  }

  // Execute short search by criteria
  btnSearchMc(): void {

    const sserchdata = this.ssearch.getRawValue();

    let code = sserchdata.sscode;
    let fullname = sserchdata.ssfullname;
    let mobile = sserchdata.ssmobile;

    let query = "";

    if (code != null && code.trim() != "") query = query + "&code=" + code;
    if (fullname != null && fullname.trim() != "") query = query + "&fullname=" + fullname;
    if (mobile != null && mobile.trim() != "") query = query + "&mobile=" + mobile;

    if (query != "") query = query.replace(/^./, "?")

    this.loadTable(query);

  }


  btnSearchClearMc(): void {

    const confirm = this.dg.open(ConfirmComponent, {
      width: '500px',
      data: {heading: "Search Clear", message: "Are you sure to Clear the Search?"}
    });

    confirm.afterClosed().subscribe(async result => {
      if (result) {
        this.ssearch.reset();
        this.loadTable("");
      }
    });

  }

  // selectImage(e: any): void {
  //   if (e.target.files) {
  //     let reader = new FileReader();
  //     reader.readAsDataURL(e.target.files[0]);
  //     reader.onload = (event: any) => {
  //       this.imageempurl = event.target.result;
  //       this.form.controls['photo'].clearValidators();
  //     }
  //   }
  // }

  // clearImage(): void {
  //   this.imageempurl = 'assets/default.png';
  //   this.form.controls['photo'].setErrors({'required': true});
  // }


  // Add a new customer after validations and confirmation
  add() {

    let errors = this.getErrors();

    if (errors != "") {
      const errmsg = this.dg.open(MessageComponent, {
        width: '500px',
        data: {heading: "Errors - Customer Add ", message: "You have following Errors <br> " + errors}
      });
      errmsg.afterClosed().subscribe(async result => {
        if (!result) {
          return;
        }
      });
    } else {

      this.customer = this.form.getRawValue();
      // this.customer.photo = btoa(this.imageempurl);

      let cusdata: string = "";

      cusdata = cusdata + "<br>Number is : " + this.customer.code;
      cusdata = cusdata + "<br>Fullname is : " + this.customer.fullname;
      cusdata = cusdata + "<br>Callingname is : " + this.customer.callingname;

      const confirm = this.dg.open(ConfirmComponent, {
        width: '500px',
        data: {
          heading: "Confirmation - Customer Add",
          message: "Are you sure to Add the following Customer? <br> <br>" + cusdata
        }
      });

      let addstatus: boolean = false;
      let addmessage: string = "Server Not Found";

      confirm.afterClosed().subscribe(async result => {
        if (result) {
          this.es.add(this.customer).then((responce: [] | undefined) => {
            if (responce != undefined) { // @ts-ignore
              console.log("Add-" + responce['id'] + "-" + responce['url'] + "-" + (responce['errors'] == ""));
              // @ts-ignore
              addstatus = responce['errors'] == "";
              console.log("Add Sta-" + addstatus);
              if (!addstatus) { // @ts-ignore
                addmessage = responce['errors'];
              }
            } else {
              console.log("undefined");
              addstatus = false;
              addmessage = "Content Not Found"
            }
          }).finally(() => {

            if (addstatus) {
              addmessage = "Successfully Saved";
              this.form.reset();
              //this.clearImage();
              Object.values(this.form.controls).forEach(control => {
                control.markAsTouched();
              });
              this.loadTable("");
            }

            const stsmsg = this.dg.open(MessageComponent, {
              width: '500px',
              data: {heading: "Status -Customer Add", message: addmessage}
            });

            stsmsg.afterClosed().subscribe(async result => {
              if (!result) {
                return;
              }
            });
          });
        }
      });
    }
  }

// Return form errors for display
  getErrors(): string {

    let errors: string = "";

    for (const controlName in this.form.controls) {
      const control = this.form.controls[controlName];
      if (control.errors) {

        if (this.regexes[controlName] != undefined) {
          errors = errors + "<br>" + this.regexes[controlName]['message'];
        } else {
          errors = errors + "<br>Invalid " + controlName;
        }
      }
    }

    return errors;
  }

// Fill form fields with selected customer data
  fillForm(customer: Customer) {

    this.selectedrow=customer;

    this.customer = JSON.parse(JSON.stringify(customer));
    this.oldcustomer = JSON.parse(JSON.stringify(customer));

    // if (this.customer.photo != null) {
    //   this.imageempurl = atob(this.customer.photo);
    //   this.form.controls['photo'].clearValidators();
    // } else {
    //   this.clearImage();
    // }
    // this.customer.photo = "";

    //@ts-ignore
    this.customer.gender = this.genders.find(g => g.id === this.customer.gender.id);
    //@ts-ignore
    this.customer.customerstatus = this.customerstatuses.find(d => d.id === this.customer.customerstatus.id);

    //@ts-ignore
    this.customer.customertype = this.customertypes.find(s => s.id === this.customer.customertype.id);

    this.form.patchValue(this.customer);
    this.form.markAsPristine();

    this.enableButtons(false,true,true);

  }

// Track what form fields were modified
  getUpdates(): string {

    let updates: string = "";
    for (const controlName in this.form.controls) {
      const control = this.form.controls[controlName];
      if (control.dirty) {
        updates = updates + "<br>" + controlName.charAt(0).toUpperCase() + controlName.slice(1)+" Changed";
      }
    }
    return updates;
  }

  // Update existing customer with confirmation
  update() {

    let errors = this.getErrors();

    if (errors != "") {
        const errmsg = this.dg.open(MessageComponent, {
          width: '500px',
          data: {heading: "Errors - Customer Update ", message: "You have following Errors <br> " + errors}
        });
        errmsg.afterClosed().subscribe(async result => { if (!result) { return; } });

    } else {

      let updates: string = this.getUpdates();

      if (updates != "") {
        let updstatus: boolean = false;
        let updmessage: string = "Server Not Found";

        const confirm = this.dg.open(ConfirmComponent, {
          width: '500px',
          data: {
            heading: "Confirmation - Customer Update",
            message: "Are you sure to Save folowing Updates? <br> <br>" + updates
          }
        });
        confirm.afterClosed().subscribe(async result => {
          if (result) {
            //console.log("CustomerService.update()");
            this.customer = this.form.getRawValue();
            if (this.form.controls['photo'].dirty) this.customer.photo = btoa(this.imageempurl);
            else this.customer.photo = this.oldcustomer.photo;
            this.customer.id = this.oldcustomer.id;

            this.es.update(this.customer).then((responce: [] | undefined) => {
              if (responce != undefined) {
                // @ts-ignore
                updstatus = responce['errors'] == "";
                if (!updstatus) { // @ts-ignore
                  updmessage = responce['errors'];
                }
              } else {
                updstatus = false;
                updmessage = "Content Not Found"
              }
            } ).finally(() => {
              if (updstatus) {
                updmessage = "Successfully Updated";
                this.form.reset();
                //this.clearImage();
                 Object.values(this.form.controls).forEach(control => { control.markAsTouched(); });
                this.loadTable("");
              }

              const stsmsg = this.dg.open(MessageComponent, {
                width: '500px',
                data: {heading: "Status -Customer Add", message: updmessage}
              });
              stsmsg.afterClosed().subscribe(async result => { if (!result) { return; } });

            });
          }
        });
    }
      else {

        const updmsg = this.dg.open(MessageComponent, {
          width: '500px',
          data: {heading: "Confirmation - Customer Update", message: "Nothing Changed"}
        });
        updmsg.afterClosed().subscribe(async result => { if (!result) { return; } });

      }
    }

  }

// Delete customer after confirmation
  delete() {

        const confirm = this.dg.open(ConfirmComponent, {
          width: '500px',
          data: {
            heading: "Confirmation - Customer Delete",
            message: "Are you sure to Delete following Customer? <br> <br>" + this.customer.callingname
          }
        });

        confirm.afterClosed().subscribe(async result => {
          if (result) {
            let delstatus: boolean = false;
            let delmessage: string = "Server Not Found";

            this.es.delete(this.customer.id).then((responce: [] | undefined) => {

                if (responce != undefined) { // @ts-ignore
                  delstatus = responce['errors'] == "";
                if (!delstatus) { // @ts-ignore
                  delmessage = responce['errors'];
                }
              } else {
                delstatus = false;
                delmessage = "Content Not Found"
              }
            } ).finally(() => {
              if (delstatus) {
                delmessage = "Successfully Deleted";
                this.form.reset();
                // this.clearImage();
                Object.values(this.form.controls).forEach(control => { control.markAsTouched(); });
                this.loadTable("");
              }

              const stsmsg = this.dg.open(MessageComponent, {
                width: '500px',
                data: {heading: "Status - Customer Delete ", message: delmessage}
              });
              stsmsg.afterClosed().subscribe(async result => { if (!result) { return; } });

            });
          }
        });
      }

  // Clear the form and reset all validations
  clear():void{
    const confirm = this.dg.open(ConfirmComponent, {
      width: '500px',
      data: {
        heading: "Confirmation - Customer Clear",
        message: "Are you sure to Clear following Details ? <br> <br>"
      }
    });

    confirm.afterClosed().subscribe(async result => {
      if (result) {
        this.form.reset();
        //this.clearImage();
        Object.keys(this.form.controls).forEach(key => {
          this.form.controls[key].clearValidators();
          this.form.controls[key].updateValueAndValidity();
        });
        this.createForm();
        if (this.form.valid) {
          this.enableButtons(true, false, false);
        } else {
          console.error("Form is not valid, cannot enable add button.");
        }
      }
    });
  }

}










