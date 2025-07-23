import {Component, ViewChild} from '@angular/core';
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
import { Purchaseorder } from 'src/app/entity/purchaseorder';
import { Postatusservice } from 'src/app/service/postatusservice';
import { Postatus } from 'src/app/entity/postatus';
import { PurchaseorderService } from 'src/app/service/Purchaseorderservice';
import { Employee } from 'src/app/entity/employee';
import { Item } from 'src/app/entity/item';
import { Poitem } from 'src/app/entity/poitem';
import { Itemservice } from 'src/app/service/itemservice';
import { EmployeeService } from 'src/app/service/employeeservice';
import { Supplier } from 'src/app/entity/supplier';
import { SupplierService } from 'src/app/service/supplierservice';
import { NumberService } from 'src/app/service/numberservice';

@Component({
  selector: 'app-purchaseorder',
  templateUrl: './purchaseorder.component.html',
  styleUrls: ['./purchaseorder.component.css']
})

export class PurchaseorderComponent {


  columns: string[] = ['po_number', 'employee', 'postatus', 'date', 'description', 'total_amount'];
  headers: string[] = ['Po_Number', 'Employee', 'Status', 'Date', 'Description', 'Expected Cost'];
  binders: string[] = ['po_number', 'employee.fullname', 'postatus.name', 'date', 'description', 'total_amount'];

  cscolumns: string[] = ['cspo_number', 'csemployee', 'cspostatus', 'csdate', 'csdescription', 'cstotal_amount'];
  csprompts: string[] = ['Search by Po_Number', 'Search by Employee', 'Search by Status', 'Search by Date', 'Search by Description', 'Search by Expected Cost'];

  incolumns: string[] = ['item', 'quantity', 'sub_total', 'remove'];
  inheaders: string[] = ['Item', 'QUANTITY', 'Line total', 'Remove',];
  inbinders: string[] = ['item.name', 'quantity', 'sub_total', 'getBtn()'];

  innerdata: any;
  oldinnerdata: any;

  indata!: MatTableDataSource<Poitem>;
  innerform!: FormGroup;
  items: Array<Item> = [];
  poitems: Array<Poitem> = [];

  today = new Date();

  public csearch!: FormGroup;
  public ssearch!: FormGroup;
  public form!: FormGroup;

  purchaseorder!: Purchaseorder;
  oldpurchaseorder!: Purchaseorder;

  purchaseorders: Array<Purchaseorder> = [];
  data!: MatTableDataSource<Purchaseorder>;
  imageurl: string = '';
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  imageempurl: string = 'assets/default.png'
  uiassist: UiAssist;

  regexes: any;
  selectedrow: any;

  postatuses: Array<Postatus> = [];
  employees: Array<Employee> = [];
  suppliers: Array<Supplier> = [];

  enaadd: boolean = false;
  enaupd: boolean = false;
  enadel: boolean = false;


  constructor(
    private pos: PurchaseorderService,
    private poss: Postatusservice,
    private itms: ItemService,
    private emps: EmployeeService,
    private sups: SupplierService,
    private rs: RegexService,
    private fb: FormBuilder,
    private dg: MatDialog,
    private dp: DatePipe,
    private ns: NumberService,
    public authService: AuthorizationManager) {

    this.uiassist = new UiAssist(this);

    this.csearch = this.fb.group({
      cspo_number: new FormControl(),
      csemployee: new FormControl(),
      cspostatus: new FormControl(),
      csdate: new FormControl(),
      csdescription: new FormControl(),
      cstotal_amount: new FormControl(),
    });

    this.ssearch = this.fb.group({
      sspo_number: new FormControl(),
      sspostatus: new FormControl(),
    });

    this.form = this.fb.group({
      "po_number": new FormControl('', [Validators.required]),
      "date": new FormControl(this.today, [Validators.required]),
      "total_amount": new FormControl('', [Validators.required]),
      "description": new FormControl('', [Validators.required]),
      "postatus": new FormControl('', [Validators.required]),
      "employee": new FormControl('', [Validators.required]),
      "supplier": new FormControl('', [Validators.required]),
    }, {updateOn: 'change'});

    this.innerform = this.fb.group({
      "item": new FormControl('', [Validators.required]),
      "quantity": new FormControl('', [Validators.required]),
      "sub_total": new FormControl('', [Validators.required]),
    }, {updateOn: 'change'});

  }

  ngOnInit() {
    this.initialize();
  }

  initialize() {

    this.createView();

    this.poss.getAllList().then((vsts: Postatus[]) => {
      this.postatuses = vsts;
    });

    this.emps.getAll('').then((vsts: Employee[]) => {
      this.employees = vsts;
    });

    this.sups.getAll('').then((vsts: Supplier[]) => {
      this.suppliers = vsts;
    });

    this.itms.getAll('').then((vsts: Item[]) => {
      this.items = vsts;
    });

    this.rs.get('purchaseorder').then((regs: []) => {
      this.regexes = regs;
      this.createForm();
    });
  }

  createView() {
    this.imageurl = 'assets/pending.gif';
    this.loadTable("");
  }

  createForm() {

    this.form.controls['po_number'].setValidators([Validators.required]);
    this.form.controls['date'].setValidators([Validators.required]);
    this.form.controls['total_amount'].setValidators([Validators.required, Validators.pattern(this.regexes['total_amount']['regex'])]);
    this.form.controls['description'].setValidators([Validators.required, Validators.pattern(this.regexes['description']['regex'])]);
    this.form.controls['postatus'].setValidators([Validators.required]);
    this.form.controls['employee'].setValidators([Validators.required]);
    this.form.controls['supplier'].setValidators([Validators.required]);

    this.innerform.controls['item'].setValidators([Validators.required]);
    this.innerform.controls['quantity'].setValidators([Validators.required, Validators.pattern(/^\d+$/)]);
    this.innerform.controls['sub_total'].setValidators([Validators.required]);


    Object.values(this.form.controls).forEach(control => {
      control.markAsTouched();
    });

    for (const controlName in this.form.controls) {
      const control = this.form.controls[controlName];
      control.valueChanges.subscribe(value => {
          // @ts-ignore
          if (controlName == "date" || controlName == "date")
            value = this.dp.transform(new Date(value), 'yyyy-MM-dd');

          if (this.oldpurchaseorder != undefined && control.valid) {
            // @ts-ignore
            if (value === this.purchaseorder[controlName]) {
              control.markAsPristine();
            } else {
              control.markAsDirty();
              console.log("hi");
            }
          } else {
            control.markAsPristine();
          }
        }
      );

    }

    this.enableButtons(true, false, false);
  }

  enableButtons(add: boolean, upd: boolean, del: boolean) {
    this.enaadd = add;
    this.enaupd = upd;
    this.enadel = del;
  }

  loadTable(query: string) {

    this.pos.getAll(query)
      .then((emps: Purchaseorder[]) => {
        this.purchaseorders = emps;
        this.ns.setLastSequenceNumber(this.purchaseorders[this.purchaseorders.length-1].po_number);
        this.generateNumber();
        this.imageurl = 'assets/fullfilled.png';
      })
      .catch((error) => {
        this.imageurl = 'assets/rejected.png';
      })
      .finally(() => {
        this.data = new MatTableDataSource(this.purchaseorders.slice().reverse());
        this.data.paginator = this.paginator;
      });

  }

  filterTable(): void {

    const cserchdata = this.csearch.getRawValue();

    this.data.filterPredicate = (purchaseorder: Purchaseorder, filter: string) => {
      // @ts-ignore
      return (cserchdata.cspo_number == null || purchaseorder.po_number.toLowerCase().includes(cserchdata.cspo_number.toLowerCase())) &&
        (cserchdata.csemployee == null || purchaseorder.employee.fullname.toLowerCase().includes(cserchdata.csemployee.toLowerCase())) &&
        (cserchdata.cspostatus == null || purchaseorder.postatus.name.toLowerCase().includes(cserchdata.cspostatus.toLowerCase())) &&
        (cserchdata.csdescription == null || purchaseorder.description.toLowerCase().includes(cserchdata.csdescription.toLowerCase())) &&
        (cserchdata.cstotal_amount == null || purchaseorder.total_amount == cserchdata.cstotal_amount)&&
        (cserchdata.csdate == null || purchaseorder.date.includes(cserchdata.csitem.toLowerCase()));
    };

    this.data.filter = 'xx';

  }

  btnSearchMc(): void {

    this.csearch.reset();
    const sserchdata = this.ssearch.getRawValue();

    let po_number = sserchdata.sspo_number;
    let postatusid = sserchdata.sspostatus;

    let query = "";

    if (po_number != null && po_number.trim() != "") query = query + "&po_number=" + po_number;
    if (postatusid != null) query = query + "&postatusid=" + postatusid;

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
        this.csearch.reset();
        this.ssearch.reset();
        this.loadTable("");
      }
    });

  }

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

  fillForm(purchaseorder: Purchaseorder) {

    this.enableButtons(false, true, true);

    this.selectedrow = purchaseorder;

    this.purchaseorder = JSON.parse(JSON.stringify(purchaseorder));
    this.oldpurchaseorder = JSON.parse(JSON.stringify(purchaseorder));


    //@ts-ignore
    this.purchaseorder.postatus = this.postatuses.find(s => s.id === this.purchaseorder.postatus.id);
    //@ts-ignore
    this.purchaseorder.employee = this.employees.find(e => e.id === this.purchaseorder.employee.id);
    //@ts-ignore
    this.purchaseorder.supplier = this.suppliers.find(e => e.id === this.purchaseorder.supplier.id);

    this.indata = new MatTableDataSource(this.purchaseorder.poitems);
    this.form.patchValue(this.purchaseorder);
    this.form.markAsPristine();
    this.calculateGrandTotal();

  }

  add() {

    let errors = this.getErrors();

    if (errors != "") {
      const errmsg = this.dg.open(MessageComponent, {
        width: '500px',
        data: {heading: "Errors - Purchaseorder Add ", message: "You have following Errors <br> " + errors}
      });
      errmsg.afterClosed().subscribe(async result => {
        if (!result) {
          return;
        }
      });
    } else {


      this.purchaseorder = this.form.getRawValue();
      this.purchaseorder.poitems = this.poitems;
      // @ts-ignore
      this.poitems.forEach((i )=> delete i.id);

      let vehdata: string = "";

      vehdata = vehdata + "<br>Po_Number is : " + this.purchaseorder.po_number;

      const confirm = this.dg.open(ConfirmComponent, {
        width: '500px',
        data: {
          heading: "Confirmation - Purchaseorder Add",
          message: "Are you sure to Add the following Purchaseorder? <br> <br>" + vehdata
        }
      });

      let addstatus: boolean = false;
      let addmessage: string = "Server Not Found";

      confirm.afterClosed().subscribe(async result => {
        if (result) {
          this.pos.add(this.purchaseorder).then((responce: [] | undefined) => {
            if (responce != undefined) {
              // @ts-ignore
              addstatus = responce['errors'] == "";
              if (!addstatus) { // @ts-ignore
                addmessage = responce['errors'];
              }
            } else {
              addstatus = false;
              addmessage = "Content Not Found"
            }
          }).finally(() => {

            if (addstatus) {
              addmessage = "Successfully Saved";
              this.form.reset();
              Object.values(this.form.controls).forEach(control => {
                control.markAsTouched();
              });
              this.loadTable("");
              this.indata.data = [];
            }

            const stsmsg = this.dg.open(MessageComponent, {
              width: '500px',
              data: {heading: "Status -Purchaseorder Add", message: addmessage}
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

  update() {

    let errors = this.getErrors();

    if (errors != "") {

      const errmsg = this.dg.open(MessageComponent, {
        width: '500px',
        data: {heading: "Errors - Purchaseorder Update ", message: "You have following Errors <br> " + errors}
      });
      errmsg.afterClosed().subscribe(async result => {
        if (!result) {
          return;
        }
      });

    } else {

      let updates: string = this.getUpdates();

      if (updates != "") {

        let updstatus: boolean = false;
        let updmessage: string = "Server Not Found";

        const confirm = this.dg.open(ConfirmComponent, {
          width: '500px',
          data: {
            heading: "Confirmation - Purchaseorder Update",
            message: "Are you sure to Save folowing Updates? <br> <br>" + updates
          }
        });
        confirm.afterClosed().subscribe(async result => {
          if (result) {
            this.purchaseorder = this.form.getRawValue();
            this.purchaseorder.poitems = this.poitems;

            this.purchaseorder.id = this.oldpurchaseorder.id;

            // @ts-ignore
            this.poitems.forEach((i)=> delete  i.id);


            // @ts-ignore
            this.purchaseorder.date = this.dp.transform(this.purchaseorder.date,"yyyy-MM-dd");

            this.pos.update(this.purchaseorder).then((responce: [] | undefined) => {
              if (responce != undefined) { // @ts-ignore
                updstatus = responce['errors'] == "";
                if (!updstatus) { // @ts-ignore
                  updmessage = responce['errors'];
                }
              } else {
                updstatus = false;
                updmessage = "Content Not Found"
              }
            }).finally(() => {
              if (updstatus) {
                updmessage = "Successfully Updated";
                this.form.reset();
                this.innerform.reset();
                this.loadTable("");
                this.indata.data = [];
                Object.values(this.form.controls).forEach(control => {
                  control.markAsTouched();
                });
              }

              const stsmsg = this.dg.open(MessageComponent, {
                width: '500px',
                data: {heading: "Status -Purchaseorder Add", message: updmessage}
              });
              stsmsg.afterClosed().subscribe(async result => {
                if (!result) {
                  return;
                }
              });

            });
          }
        });
      } else {

        const updmsg = this.dg.open(MessageComponent, {
          width: '500px',
          data: {heading: "Confirmation - Purchaseorder Update", message: "Nothing Changed"}
        });
        updmsg.afterClosed().subscribe(async result => {
          if (!result) {
            return;
          }
        });

      }
    }


  }

  getUpdates(): string {

    let updates: string = "";
    for (const controlName in this.form.controls) {
      const control = this.form.controls[controlName];
      if (control.dirty) {
        updates = updates + "<br>" + controlName.charAt(0).toUpperCase() + controlName.slice(1) + " Changed";
      }
    }
    return updates;
  }

  delete() {

    const confirm = this.dg.open(ConfirmComponent, {
      width: '500px',
      data: {
        heading: "Confirmation - Purchaseorder Delete",
        message: "Are you sure to Delete following Purchaseorder? <br> <br>" + this.purchaseorder.po_number
      }
    });

    confirm.afterClosed().subscribe(async result => {
      if (result) {
        let delstatus: boolean = false;
        let delmessage: string = "Server Not Found";

        this.pos.delete(this.purchaseorder.id).then((responce: [] | undefined) => {

          if (responce != undefined) { // @ts-ignore
            delstatus = responce['errors'] == "";
            if (!delstatus) { // @ts-ignore
              delmessage = responce['errors'];
            }
          } else {
            delstatus = false;
            delmessage = "Content Not Found"
          }
        }).finally(() => {
          if (delstatus) {
            delmessage = "Successfully Deleted";
            this.form.reset();
            Object.values(this.form.controls).forEach(control => {
              control.markAsTouched();
            });
            this.loadTable("");
            this.indata.data = [];
          }

          const stsmsg = this.dg.open(MessageComponent, {
            width: '500px',
            data: {heading: "Status - Purchaseorder Delete ", message: delmessage}
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

  clear(): void {
    const confirm = this.dg.open(ConfirmComponent, {
      width: '500px',
      data: {
        heading: "Confirmation - Purchaseorder Clear",
        message: "Are you sure to Clear following Details ? <br> <br>"
      }
    });

    confirm.afterClosed().subscribe(async result => {
      if (result) {
        this.form.reset()
        window.location.reload();
      }
    });
    this.enableButtons(true, false, false);
  }

  //inner table

  id = 0;

  btnaddMc() {

    this.innerdata = this.innerform.getRawValue();
    console.log(this.innerdata);

    if (this.innerdata != null) {

      let sub_total =this.innerdata.quantity * this.innerdata.sub_total;
      let poitem = new Poitem(this.id,this.innerdata.quantity, sub_total, this.innerdata.item);

      let tem: Poitem[] = [];
      if (this.indata != null) this.indata.data.forEach((i) => tem.push(i));

      this.poitems = [];
      tem.forEach((t) => this.poitems.push(t));

      this.poitems.push(poitem);
      this.indata = new MatTableDataSource(this.poitems);

      this.id++;

      this.calculateGrandTotal();
      this.innerform.reset();

    }

  }

  calculateGrandTotal() {

    let total_amount=0;
    this.indata.data.forEach((m) => {
      total_amount = total_amount + m.sub_total
    })
    let roundedValue = parseFloat(total_amount.toString()).toFixed(2);
    this.form.controls['total_amount'].setValue(roundedValue);
  }

  deleteRaw(x: any) {

    let datasources = this.indata.data

    const index = datasources.findIndex(m => m.id === x.id);
    if (index > -1) {
      datasources.splice(index, 1);
    }
    this.indata.data = datasources;
    this.poitems = this.indata.data;

    this.calculateGrandTotal();
  }

  filterlinecost(){
    let i = this.innerform.controls['item'].value.pprice;
    let roundedValue = parseFloat(i).toFixed(2);
    this.innerform.controls['sub_total'].setValue(roundedValue);
  }

  generateNumber(): void {
    const newNumber = this.ns.generateNumber('POR');
    this.form.controls['po_number'].setValue(newNumber);
  }

  filteritem(){
    let supplier = this.form.controls['supplier'].value.id;
    this.itms.getItemBySupplier(supplier).then((msys: Item[]) => {
      this.items = msys;
    });
  }

}












