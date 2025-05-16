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
import {DesignationService} from "../../../service/designationservice";
import {MatDialog} from "@angular/material/dialog";
import {MessageComponent} from "../../../util/dialog/message/message.component";
import {ConfirmComponent} from "../../../util/dialog/confirm/confirm.component";
import {Cusstatus} from "../../../entity/cusstatus";
import {RegexService} from "../../../service/regexservice";
import {DatePipe} from "@angular/common";
import {AuthorizationManager} from "../../../service/authorizationmanager";
import {Custype} from "../../../entity/custype";
import {Cusstatusservice} from "../../../service/cusstatusservice";
import {Custypeservice} from "../../../service/custypeservice";


@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.css']
})

export class CustomerComponent {

  columns: string[] = ['code', 'callingname', 'gender', 'mobile', 'email', 'address'];
  headers: string[] = ['Code', 'Calling Name', 'Gender', 'Mobile', 'Email', 'Address'];
  binders: string[] = ['code', 'callingname', 'gender.name', 'mobile', 'email', 'address'];

  cscolumns: string[] = ['csnumber', 'cscallingname', 'csgender', 'csdesignation', 'csname', 'csmodi'];
  csprompts: string[] = ['Search by Number', 'Search by Name', 'Search by Gender',
    'Search by Designation', 'Search by Full Name', 'Search by Modi'];

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

  enaadd: boolean = false;
  enaupd: boolean = false;
  enadel: boolean = false;

  hasInsertAuthority: boolean = false;
  hasUpdateAuthority: boolean = false;
  hasDeleteAuthority: boolean = false;

  genders: Array<Gender> = [];
  designations: Array<Designation> = [];
  customerstatuses: Array<Cusstatus> = [];
  customertypes: Array<Custype> = [];

  regexes: any;

  uiassist: UiAssist;

  //mindate!:Date;

  constructor(
    private es: CustomerService,
    private gs: GenderService,
    private ds: DesignationService,
    private ss: Cusstatusservice,
    private et: Custypeservice,
    private rs: RegexService,
    private fb: FormBuilder,
    private dg: MatDialog,
    private dp: DatePipe,
    public authService:AuthorizationManager) {

    this.uiassist = new UiAssist(this);

    this.csearch = this.fb.group({
      "csnumber": new FormControl(),
      "cscallingname": new FormControl(),
      "csgender": new FormControl(),
      "csdesignation": new FormControl(),
      "csname": new FormControl(),
      "csmodi": new FormControl(),
    });

    this.ssearch = this.fb.group({
      "ssnumber": new FormControl(),
      "ssfullname": new FormControl(),
      "ssgender": new FormControl(),
      "ssdesignation": new FormControl(),
      "ssnic": new FormControl()
    });


    this.form = this.fb.group({
      "code": new FormControl('', [Validators.required]),
      "fullname": new FormControl('', [Validators.required]),
      "callingname": new FormControl('', [Validators.required]),
      "gender": new FormControl('', [Validators.required]),
      "nic": new FormControl('', [Validators.required]),
      "dobirth": new FormControl('', [Validators.required]),
      "photo": new FormControl('', [Validators.required]),
      "mobile": new FormControl('', [Validators.required]),
      "land": new FormControl('', ),
      "email": new FormControl('', [Validators.required]),
      "designation": new FormControl('', [Validators.required]),
      "doassignment": new FormControl('', [Validators.required]),
      "description": new FormControl('', [Validators.required]),
      "custype": new FormControl('', [Validators.required]),
      "cusstatus": new FormControl('', [Validators.required]),
    }, {updateOn: 'change'});


  }

  ngOnInit() {
    this.initialize();
  }

  initialize() {

    const authoritiesArray = this.authService.getAuthorities();
    if (authoritiesArray !== undefined && Array.isArray(authoritiesArray)) {
      const authorities = this.authService.extractAuthorities(authoritiesArray);
      this.buttonStates(authorities);
    }

    this.createView();

    this.gs.getAllList().then((gens: Gender[]) => {
      this.genders = gens;
    });

    this.ds.getAllList().then((dess: Designation[]) => {
      this.designations = dess;
    });

    this.rs.get('customers').then((regs: []) => {
      this.regexes = regs;
      //this.createForm();
    });

  }

  buttonStates(authorities: { module: string; operation: string }[]): void {
    this.hasInsertAuthority = authorities.some(authority => authority.module === 'customer' && authority.operation === 'insert');
    this.hasUpdateAuthority = authorities.some(authority => authority.module === 'customer' && authority.operation === 'update');
    this.hasDeleteAuthority = authorities.some(authority => authority.module === 'customer' && authority.operation === 'delete');

  }

  createView() {
    this.imageurl = 'assets/pending.gif';
    this.loadTable("");
  }


  // createForm() {
  //
  //   this.form.controls['number'].setValidators([Validators.required, Validators.pattern(this.regexes['number']['regex'])]);
  //   this.form.controls['fullname'].setValidators([Validators.required, Validators.pattern(this.regexes['fullname']['regex'])]);
  //   this.form.controls['callingname'].setValidators([Validators.required, Validators.pattern(this.regexes['callingname']['regex'])]);
  //   this.form.controls['gender'].setValidators([Validators.required]);
  //   this.form.controls['nic'].setValidators([Validators.required, Validators.pattern(this.regexes['nic']['regex'])]);
  //   this.form.controls['dobirth'].setValidators([Validators.required]);
  //   this.form.controls['photo'].setValidators([Validators.required]);
  //   this.form.controls['address'].setValidators([Validators.required, Validators.pattern(this.regexes['address']['regex'])]);
  //   this.form.controls['mobile'].setValidators([Validators.required, Validators.pattern(this.regexes['mobile']['regex'])]);
  //   this.form.controls['land'].setValidators([Validators.required,Validators.pattern(this.regexes['land']['regex'])]);
  //   this.form.controls['email'].setValidators([Validators.required,Validators.pattern(this.regexes['email']['regex'])]);
  //   this.form.controls['designation'].setValidators([Validators.required]);
  //   this.form.controls['doassignment'].setValidators([Validators.required]);
  //   this.form.controls['description'].setValidators([Validators.required, Validators.pattern(this.regexes['description']['regex'])]);
  //   this.form.controls['custype'].setValidators([Validators.required]);
  //   this.form.controls['cusstatus'].setValidators([Validators.required]);
  //
  //   Object.values(this.form.controls).forEach( control => { control.markAsTouched(); } );
  //
  //   for (const controlName in this.form.controls) {
  //     const control = this.form.controls[controlName];
  //     control.valueChanges.subscribe(value => {
  //           // @ts-ignore
  //           if (controlName == "dobirth" || controlName == "doassignment")
  //               value = this.dp.transform(new Date(value), 'yyyy-MM-dd');
  //
  //           if (this.oldcustomer != undefined && control.valid) {
  //             // @ts-ignore
  //             if (value === this.customer[controlName]) {
  //               control.markAsPristine();
  //             } else {
  //               control.markAsDirty();
  //             }
  //           } else {
  //             control.markAsPristine();
  //           }
  //         }
  //     );
  //
  //     }
  //
  //   this.enableButtons(true,false,false);
  //
  // }


  // enableButtons(add:boolean, upd:boolean, del:boolean){
  //   this.enaadd=add;
  //   this.enaupd=upd;
  //   this.enadel=del;
  // }

  loadTable(query: string) {

    this.es.getAll(query)
      .then((emps: Customer[]) => {
        this.customers = emps;
        this.imageurl = 'assets/fullfilled.png';
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
      return (cserchdata.csnumber == null || customer.code.toLowerCase().includes(cserchdata.csnumber)) &&
        (cserchdata.cscallingname == null || customer.callingname.toLowerCase().includes(cserchdata.cscallingname)) &&
        (cserchdata.csgender == null || customer.gender.name.toLowerCase().includes(cserchdata.csgender)) &&
        (cserchdata.csname == null || customer.fullname.toLowerCase().includes(cserchdata.csname)) &&
        (cserchdata.csmodi == null || this.getModi(customer).toLowerCase().includes(cserchdata.csmodi));
    };

    this.data.filter = 'xx';

  }

  // btnSearchMc(): void {
  //
  //   const sserchdata = this.ssearch.getRawValue();
  //
  //   let number = sserchdata.ssnumber;
  //   let fullname = sserchdata.ssfullname;
  //   let nic = sserchdata.ssnic;
  //   let genderid = sserchdata.ssgender;
  //   let designationid = sserchdata.ssdesignation;
  //
  //   let query = "";
  //
  //   if (number != null && number.trim() != "") query = query + "&number=" + number;
  //   if (fullname != null && fullname.trim() != "") query = query + "&fullname=" + fullname;
  //   if (nic != null && nic.trim() != "") query = query + "&nic=" + nic;
  //   if (genderid != null) query = query + "&genderid=" + genderid;
  //   if (designationid != null) query = query + "&designationid=" + designationid;
  //
  //   if (query != "") query = query.replace(/^./, "?")
  //
  //   this.loadTable(query);
  //
  // }


  // btnSearchClearMc(): void {
  //
  //   const confirm = this.dg.open(ConfirmComponent, {
  //     width: '500px',
  //     data: {heading: "Search Clear", message: "Are you sure to Clear the Search?"}
  //   });
  //
  //   confirm.afterClosed().subscribe(async result => {
  //     if (result) {
  //       this.ssearch.reset();
  //       this.loadTable("");
  //     }
  //   });
  //
  // }

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


  // add() {
  //
  //   let errors = this.getErrors();
  //
  //   if (errors != "") {
  //     const errmsg = this.dg.open(MessageComponent, {
  //       width: '500px',
  //       data: {heading: "Errors - Customer Add ", message: "You have following Errors <br> " + errors}
  //     });
  //     errmsg.afterClosed().subscribe(async result => {
  //       if (!result) {
  //         return;
  //       }
  //     });
  //   } else {
  //
  //     this.customer = this.form.getRawValue();
  //     this.customer.photo = btoa(this.imageempurl);
  //
  //     let cusdata: string = "";
  //
  //     cusdata = cusdata + "<br>Number is : " + this.customer.code;
  //     cusdata = cusdata + "<br>Fullname is : " + this.customer.fullname;
  //     cusdata = cusdata + "<br>Callingname is : " + this.customer.callingname;
  //
  //     const confirm = this.dg.open(ConfirmComponent, {
  //       width: '500px',
  //       data: {
  //         heading: "Confirmation - Customer Add",
  //         message: "Are you sure to Add the following Customer? <br> <br>" + cusdata
  //       }
  //     });
  //
  //     let addstatus: boolean = false;
  //     let addmessage: string = "Server Not Found";
  //
  //     confirm.afterClosed().subscribe(async result => {
  //       if (result) {
  //         this.es.add(this.customer).then((responce: [] | undefined) => {
  //           if (responce != undefined) { // @ts-ignore
  //             console.log("Add-" + responce['id'] + "-" + responce['url'] + "-" + (responce['errors'] == ""));
  //             // @ts-ignore
  //             addstatus = responce['errors'] == "";
  //             console.log("Add Sta-" + addstatus);
  //             if (!addstatus) { // @ts-ignore
  //               addmessage = responce['errors'];
  //             }
  //           } else {
  //             console.log("undefined");
  //             addstatus = false;
  //             addmessage = "Content Not Found"
  //           }
  //         }).finally(() => {
  //
  //           if (addstatus) {
  //             addmessage = "Successfully Saved";
  //             this.form.reset();
  //             this.clearImage();
  //             Object.values(this.form.controls).forEach(control => {
  //               control.markAsTouched();
  //             });
  //             this.loadTable("");
  //           }
  //
  //           const stsmsg = this.dg.open(MessageComponent, {
  //             width: '500px',
  //             data: {heading: "Status -Customer Add", message: addmessage}
  //           });
  //
  //           stsmsg.afterClosed().subscribe(async result => {
  //             if (!result) {
  //               return;
  //             }
  //           });
  //         });
  //       }
  //     });
  //   }
  // }


  // getErrors(): string {
  //
  //   let errors: string = "";
  //
  //   for (const controlName in this.form.controls) {
  //     const control = this.form.controls[controlName];
  //     if (control.errors) {
  //
  //       if (this.regexes[controlName] != undefined) {
  //         errors = errors + "<br>" + this.regexes[controlName]['message'];
  //       } else {
  //         errors = errors + "<br>Invalid " + controlName;
  //       }
  //     }
  //   }
  //
  //   return errors;
  // }


  // fillForm(customer: Customer) {
  //
  //   this.selectedrow=customer;
  //
  //   this.customer = JSON.parse(JSON.stringify(customer));
  //   this.oldcustomer = JSON.parse(JSON.stringify(customer));
  //
  //   if (this.customer.photo != null) {
  //     this.imageempurl = atob(this.customer.photo);
  //     this.form.controls['photo'].clearValidators();
  //   } else {
  //     this.clearImage();
  //   }
  //   this.customer.photo = "";
  //
  //   //@ts-ignore
  //   this.customer.gender = this.genders.find(g => g.id === this.customer.gender.id);
  //   //@ts-ignore
  //   this.customer.designation = this.designations.find(d => d.id === this.customer.designation.id);
  //
  //   //@ts-ignore
  //   this.customer.emptype = this.customertypes.find(s => s.id === this.customer.emptype.id);
  //
  //   this.form.patchValue(this.customer);
  //   this.form.markAsPristine();
  //
  //   this.enableButtons(false,true,true);
  //
  // }
  //
  //
  // getUpdates(): string {
  //
  //   let updates: string = "";
  //   for (const controlName in this.form.controls) {
  //     const control = this.form.controls[controlName];
  //     if (control.dirty) {
  //       updates = updates + "<br>" + controlName.charAt(0).toUpperCase() + controlName.slice(1)+" Changed";
  //     }
  //   }
  //   return updates;
  // }
  //
  //
  // update() {
  //
  //   let errors = this.getErrors();
  //
  //   if (errors != "") {
  //
  //       const errmsg = this.dg.open(MessageComponent, {
  //         width: '500px',
  //         data: {heading: "Errors - Customer Update ", message: "You have following Errors <br> " + errors}
  //       });
  //       errmsg.afterClosed().subscribe(async result => { if (!result) { return; } });
  //
  //   } else {
  //
  //     let updates: string = this.getUpdates();
  //
  //     if (updates != "") {
  //
  //       let updstatus: boolean = false;
  //       let updmessage: string = "Server Not Found";
  //
  //       const confirm = this.dg.open(ConfirmComponent, {
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
  //           else this.customer.photo = this.oldcustomer.photo;
  //           this.customer.id = this.oldcustomer.id;
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
  //               this.clearImage();
  //                Object.values(this.form.controls).forEach(control => { control.markAsTouched(); });
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
  //   }
  //     else {
  //
  //       const updmsg = this.dg.open(MessageComponent, {
  //         width: '500px',
  //         data: {heading: "Confirmation - Customer Update", message: "Nothing Changed"}
  //       });
  //       updmsg.afterClosed().subscribe(async result => { if (!result) { return; } });
  //
  //     }
  //   }
  //
  // }
  //
  //
  // delete() {
  //
  //       const confirm = this.dg.open(ConfirmComponent, {
  //         width: '500px',
  //         data: {
  //           heading: "Confirmation - Customer Delete",
  //           message: "Are you sure to Delete following Customer? <br> <br>" + this.customer.callingname
  //         }
  //       });
  //
  //       confirm.afterClosed().subscribe(async result => {
  //         if (result) {
  //           let delstatus: boolean = false;
  //           let delmessage: string = "Server Not Found";
  //
  //           this.es.delete(this.customer.id).then((responce: [] | undefined) => {
  //
  //               if (responce != undefined) { // @ts-ignore
  //                 delstatus = responce['errors'] == "";
  //               if (!delstatus) { // @ts-ignore
  //                 delmessage = responce['errors'];
  //               }
  //             } else {
  //               delstatus = false;
  //               delmessage = "Content Not Found"
  //             }
  //           } ).finally(() => {
  //             if (delstatus) {
  //               delmessage = "Successfully Deleted";
  //               this.form.reset();
  //               this.clearImage();
  //               Object.values(this.form.controls).forEach(control => { control.markAsTouched(); });
  //               this.loadTable("");
  //             }
  //
  //             const stsmsg = this.dg.open(MessageComponent, {
  //               width: '500px',
  //               data: {heading: "Status - Customer Delete ", message: delmessage}
  //             });
  //             stsmsg.afterClosed().subscribe(async result => { if (!result) { return; } });
  //
  //           });
  //         }
  //       });
  //     }
  //
  // clear():void{
  //   const confirm = this.dg.open(ConfirmComponent, {
  //     width: '500px',
  //     data: {
  //       heading: "Confirmation - Customer Clear",
  //       message: "Are you sure to Clear following Details ? <br> <br>"
  //     }
  //   });
  //
  //   confirm.afterClosed().subscribe(async result => {
  //     if (result) {
  //       this.form.reset();
  //       this.clearImage();
  //        this.createForm();
  //     }
  //   });
  // }


}










