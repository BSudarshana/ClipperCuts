// import { Component } from '@angular/core';
import {Component, ViewChild} from '@angular/core';

import {Supplier} from "../../../entity/supplier";
import {Supplierstype} from "../../../entity/supplierstype";
import {Supplierstate} from "../../../entity/supplierstate";
import {SupplierService} from "../../../service/supplierservice";
import {Supplierstateservice} from "../../../service/supplierstateservice";
import {Supplierstypeservice} from "../../../service/supplierstypeservice";

import {MatPaginator} from "@angular/material/paginator";
import {MatTableDataSource} from "@angular/material/table";
import {FormBuilder, FormControl, FormGroup, ValidationErrors, Validators} from "@angular/forms";
import {UiAssist} from "../../../util/ui/ui.assist";
import {MatDialog} from "@angular/material/dialog";
import {MessageComponent} from "../../../util/dialog/message/message.component";
import {ConfirmComponent} from "../../../util/dialog/confirm/confirm.component";

import {RegexService} from "../../../service/regexservice";
import {DatePipe} from "@angular/common";
import {AuthorizationManager} from "../../../service/authorizationmanager";

import {NumberService} from "../../../service/numberservice";
import {Customerstatus} from "../../../entity/customerstatus";
import {Customertype} from "../../../entity/customertype";

@Component({
  selector: 'app-supplier',
  templateUrl: './supplier.component.html',
  styleUrls: ['./supplier.component.css']
})
export class SupplierComponent {

  // Table column keys, headers, and data bindings
  columns: string[] = ['registernumber', 'name','address', 'contactperson', 'contactnumber', 'description'];
  headers: string[] = ['Reg number', 'Name','Address' , 'Contact person', 'Contact number', 'Description'];
  binders: string[] = ['registernumber', 'name','address', 'contactperson', 'contactnumber', 'description'];

  // cscolumns: string[] = ['cscode', 'cscallingname', 'csgender', 'csdesignation', 'csname', 'csmodi'];
  cscolumns: string[] = ['csregnumber', 'csname','csaddress', 'cscontactperson', 'cscontactnumber', 'csdescription'];
  csprompts: string[] = ['Search by Code', 'Search by Name', 'Search by Address', 'Search by contact person', 'Search by Phone', 'description'];

  // Form groups for searching and Supplier entry
  public csearch!: FormGroup;
  public ssearch!: FormGroup;
  public form!: FormGroup;

  supplier!: Supplier;
  oldsupplier!: Supplier;

  selectedrow: any;

  suppliers: Array<Supplier> = [];
  data!: MatTableDataSource<Supplier>;
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
  supplierstates: Array<Supplierstate> = [];
  supplierstypes: Array<Supplierstype> = [];

  // Regex pattern holder
  regexes: any;

  uiassist: UiAssist;

  //mindate!:Date;

  constructor(
    private suppService: SupplierService,
    private suppstateService: Supplierstateservice,
    private supptypeService: Supplierstypeservice,
    private regexService: RegexService,
    private formbuilder: FormBuilder,
    private matdialog: MatDialog,
    private datepipe: DatePipe,
    private numberService: NumberService,
    public authService:AuthorizationManager) {

    // Initialize UI helper class
    this.uiassist = new UiAssist(this);

    // Create Supplier search form
    this.csearch = this.formbuilder.group({
      "csregnumber": new FormControl(),
      "csname": new FormControl(),
      "csaddress": new FormControl(),
      "cscontactperson": new FormControl(),
      "cscontactnumber": new FormControl(),
      "csdescription": new FormControl(),
    });


    // Create short search filters
    this.ssearch = this.formbuilder.group({
      "sscode": new FormControl(),
      "ssname": new FormControl()
    });

    // Create supplier form with basic validations
    this.form = this.formbuilder.group({
      "registernumber": new FormControl('', [Validators.required]),
      "name": new FormControl('', [Validators.required]),
      "address": new FormControl('', [Validators.required]),
      "contactperson": new FormControl('', [Validators.required]),
      "contactnumber": new FormControl('', [Validators.required]),
      "email": new FormControl('', [Validators.required]),
      "description": new FormControl('', [Validators.required]),
      "suplierstate": new FormControl('', [Validators.required]),
      "supplierstype": new FormControl('', [Validators.required]),
    }, {updateOn: 'change'});

  }

  // Initialize supplier form
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

    // Fetch dropdown values for Supplier status
    this.suppstateService.getAllList().then((stes: Supplierstate[]) => {
      this.supplierstates = stes;
    });

    // Fetch dropdown values for supplier type
    this.supptypeService.getAllList().then((types: Supplierstype[]) => {
      this.supplierstypes = types;
    });

    // Load regex validations
    this.regexService.get('supplier').then((regs: []) => {
      this.regexes = regs;
      this.createForm();
    });

  }

  // Determine button access based on user roles
  buttonStates(authorities: { module: string; operation: string }[]): void {
    this.hasInsertAuthority = authorities.some(authority => authority.module === 'supplier' && authority.operation === 'insert');
    this.hasUpdateAuthority = authorities.some(authority => authority.module === 'supplier' && authority.operation === 'update');
    this.hasDeleteAuthority = authorities.some(authority => authority.module === 'supplier' && authority.operation === 'delete');

  }

  createView() {
    this.imageurl = 'assets/pending.gif';
    this.loadTable("");
  }

// Assign regex validations dynamically and detect field changes
  createForm() {
    this.form.controls['registernumber'].setValidators([Validators.required]);
    this.form.controls['name'].setValidators([Validators.required, Validators.pattern(this.regexes['name']['regex'])]);
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
            value = this.datepipe.transform(new Date(value), 'yyyy-MM-dd');

          if (this.oldsupplier != undefined && control.valid) {
            // @ts-ignore
            if (value === this.supplier[controlName]) {
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

  // Fetch Supplier records and load into the table
  loadTable(query: string) {
    this.suppService.getAll(query)
      .then((cuss: Supplier[]) => {
        this.suppliers = cuss;
        this.imageurl = 'assets/fullfilled.png';
        this.numberService.setLastSequenceNumber(this.suppliers[this.suppliers.length-1].registernumber);
        this.generateNumber();
      })
      .catch((error) => {
        console.log(error);
        this.imageurl = 'assets/rejected.png';
      })
      .finally(() => {
        this.data = new MatTableDataSource(this.suppliers);
        this.data.paginator = this.paginator;
      });

  }

  getModi(element: Supplier) {
    return element.registernumber + '(' + element.name + ')';
  }


  filterTable(): void {

    const supserchdata = this.csearch.getRawValue();

    this.data.filterPredicate = (supplier: Supplier, filter: string) => {
      return (supserchdata.cscode == null || supplier.registernumber.includes(supserchdata.cscode)) &&
        (supserchdata.csfullname == null || supplier.name.toLowerCase().includes(supserchdata.csfullname)) &&
        (supserchdata.csgender == null || supplier.supplierstype.name.toLowerCase().includes(supserchdata.csgender)) &&
        (supserchdata.csmobile == null || supplier.contactnumber.toLowerCase().includes(supserchdata.csmobile)) &&
        (supserchdata.csemail == null || supplier.email.includes(supserchdata.csemail)) &&
        (supserchdata.csaddress == null || supplier.address.toLowerCase().includes(supserchdata.csaddress)) ;
    };

    this.data.filter = 'xx';

  }

  // Generate new customer code
  generateNumber(): void{
    const newNumber : string = this.numberService.generateNumber('S');
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

    const confirm = this.matdialog.open(ConfirmComponent, {
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



  // Add a new customer after validations and confirmation
  add() {

    let errors = this.getErrors();

    if (errors != "") {
      const errmsg = this.matdialog.open(MessageComponent, {
        width: '500px',
        data: {heading: "Errors - Customer Add ", message: "You have following Errors <br> " + errors}
      });
      errmsg.afterClosed().subscribe(async result => {
        if (!result) {
          return;
        }
      });
    } else {

      this.supplier = this.form.getRawValue();
      // this.customer.photo = btoa(this.imageempurl);

      let cusdata: string = "";

      cusdata = cusdata + "<br>Number is : " + this.supplier.registernumber;
      cusdata = cusdata + "<br>Name is : " + this.supplier.name;

      const confirm = this.matdialog.open(ConfirmComponent, {
        width: '500px',
        data: {
          heading: "Confirmation - Supplier Add",
          message: "Are you sure to Add the following Customer? <br> <br>" + cusdata
        }
      });

      let addstatus: boolean = false;
      let addmessage: string = "Server Not Found";

      confirm.afterClosed().subscribe(async result => {
        if (result) {
          this.suppService.add(this.supplier).then((responce: [] | undefined) => {
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

            const stsmsg = this.matdialog.open(MessageComponent, {
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
  fillForm(supplier: Supplier) {

    this.selectedrow=supplier;

    this.supplier = JSON.parse(JSON.stringify(supplier));
    this.oldsupplier = JSON.parse(JSON.stringify(supplier));

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

    this.form.patchValue(this.supplier);
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

  // Update existing Supplier with confirmation
  // update() {
  //
  //   let errors = this.getErrors();
  //
  //   if (errors != "") {
  //     const errmsg = this.matdialog.open(MessageComponent, {
  //       width: '500px',
  //       data: {heading: "Errors - Customer Update ", message: "You have following Errors <br> " + errors}
  //     });
  //     errmsg.afterClosed().subscribe(async result => { if (!result) { return; } });
  //
  //   } else {
  //
  //     let updates: string = this.getUpdates();
  //
  //     if (updates != "") {
  //       let updstatus: boolean = false;
  //       let updmessage: string = "Server Not Found";
  //
  //       const confirm = this.matdialog.open(ConfirmComponent, {
  //         width: '500px',
  //         data: {
  //           heading: "Confirmation - Customer Update",
  //           message: "Are you sure to Save folowing Updates? <br> <br>" + updates
  //         }
  //       });
  //       confirm.afterClosed().subscribe(async result => {
  //         if (result) {
  //           //console.log("CustomerService.update()");
  //           this.customer = this.form.getRawValue();
  //           if (this.form.controls['photo'].dirty) this.customer.photo = btoa(this.imageempurl);
  //           else this.customer.photo = this.oldsupplier.photo;
  //           this.customer.id = this.oldsupplier.id;
  //
  //           this.es.update(this.customer).then((responce: [] | undefined) => {
  //             if (responce != undefined) {
  //               // @ts-ignore
  //               updstatus = responce['errors'] == "";
  //               if (!updstatus) { // @ts-ignore
  //                 updmessage = responce['errors'];
  //               }
  //             } else {
  //               updstatus = false;
  //               updmessage = "Content Not Found"
  //             }
  //           } ).finally(() => {
  //             if (updstatus) {
  //               updmessage = "Successfully Updated";
  //               this.form.reset();
  //               //this.clearImage();
  //               Object.values(this.form.controls).forEach(control => { control.markAsTouched(); });
  //               this.loadTable("");
  //             }
  //
  //             const stsmsg = this.dg.open(MessageComponent, {
  //               width: '500px',
  //               data: {heading: "Status -Customer Add", message: updmessage}
  //             });
  //             stsmsg.afterClosed().subscribe(async result => { if (!result) { return; } });
  //
  //           });
  //         }
  //       });
  //     }
  //     else {
  //
  //       const updmsg = this.matdialog.open(MessageComponent, {
  //         width: '500px',
  //         data: {heading: "Confirmation - Customer Update", message: "Nothing Changed"}
  //       });
  //       updmsg.afterClosed().subscribe(async result => { if (!result) { return; } });
  //
  //     }
  //   }
  //
  // }

// Delete customer after confirmation
  delete() {

    const confirm = this.matdialog.open(ConfirmComponent, {
      width: '500px',
      data: {
        heading: "Confirmation - Customer Delete",
        message: "Are you sure to Delete following Supplier? <br> <br>" + this.supplier.name
      }
    });

    confirm.afterClosed().subscribe(async result => {
      if (result) {
        let delstatus: boolean = false;
        let delmessage: string = "Server Not Found";

        this.suppService.delete(this.supplier.id).then((responce: [] | undefined) => {

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

          const stsmsg = this.matdialog.open(MessageComponent, {
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
    const confirm = this.matdialog.open(ConfirmComponent, {
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
