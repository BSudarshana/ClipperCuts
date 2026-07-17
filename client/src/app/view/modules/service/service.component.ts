import {Component, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {RegexService} from "../../../service/regexservice";
import {MatDialog} from "@angular/material/dialog";
import {DatePipe} from "@angular/common";
import {NumberService} from "../../../service/numberservice";
import {AuthorizationManager} from "../../../service/authorizationmanager";
import {ServiceStatusService} from "../../../service/serviceStatusService";
import {ServiceCategoryService} from "../../../service/serviceCategoryService";
import {ServiceService} from "../../../service/serviceService";
import {Service} from "../../../entity/service";
import {Servicestatus} from "../../../entity/servicestatus";
import {Servicecategory} from "../../../entity/servicecategory";
import {MessageComponent} from "../../../util/dialog/message/message.component";
import {ConfirmComponent} from "../../../util/dialog/confirm/confirm.component";

import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {UiAssist} from "../../../util/ui/ui.assist";
import {Supplier} from "../../../entity/supplier";

@Component({
  selector: 'app-service',
  templateUrl: './service.component.html',
  styleUrls: ['./service.component.css']
})
export class ServiceComponent {
  public form !: FormGroup;
  public serviceSearchForm !: FormGroup;
  public serviceFilterForm !: FormGroup;

  public service !: Service;
  public oldservice !: Service;
  selectedrow: any;

  services : Array<Service> = [];
  data!: MatTableDataSource<Service>;
  imageurl: string = '';
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  // Button enable flags
  enaadd: boolean = false;
  enaupd: boolean = false;
  enadel: boolean = false;

  hasInsertAuthority: boolean = false;
  hasUpdateAuthority: boolean = false;
  hasDeleteAuthority: boolean = false;

  //Dropdown Data
  public serviceStatuses : Array<Servicestatus> = [];
  public serviceCategories : Array<Servicecategory> = [];

  // Regex pattern holder
  regexes: any;

  uiassist: UiAssist;


  // Table column keys, headers, and data bindings
  columns: string[] = ['code', 'name','duration', 'price'];
  headers: string[] = ['Service Code', 'Service Name','Duration' , 'Price'];
  binders: string[] = ['code', 'name','duration', 'price'];

  cscolumns: string[] = ['flcode', 'flname','flduration', 'flprice'];
  csprompts: string[] = ['Filter by Code', 'Filter by Name', 'Filter by Duration', 'Filter by Price'];

  constructor(
    private serviceStatusService : ServiceStatusService,
    private serviceCategoryService : ServiceCategoryService,
    private serviceService : ServiceService,
    private regexService: RegexService,
    private formbuilder: FormBuilder,
    private matdialog: MatDialog,
    private datepipe: DatePipe,
    private numberService: NumberService,
    public authService:AuthorizationManager) {

    this.uiassist = new UiAssist(this);

    this.form = this.formbuilder.group({
      "code": new FormControl('', [Validators.required]),
      "name": new FormControl('', [Validators.required]),
      "duration": new FormControl('', [Validators.required]),
      "price": new FormControl('', [Validators.required]),
      "servicecategory": new FormControl('', [Validators.required]),
      "servicestatus": new FormControl('', [Validators.required])

    }, {updateOn: 'change'});

    this.serviceSearchForm = this.formbuilder.group({
      "srcode": new FormControl(),
      "srname" : new FormControl()
    })

    this.serviceFilterForm = this.formbuilder.group({
      "flcode" : new FormControl(),
      "flname" : new FormControl(),
      "flduration" : new FormControl(),
      "flprice" : new FormControl()
    });


    // this.form.markAsPristine();



  }

  ngOnInit(){
    this.initialize();
    this.enableButtons(true, false, false)
  }

  initialize(){
    this.createView();

    const authoritiesArray = this.authService.getAuthorities();
    if (authoritiesArray !== undefined && Array.isArray(authoritiesArray)) {
      const authorities = this.authService.extractAuthorities(authoritiesArray);
      this.buttonStates(authorities);
    }

    // Fetch dropdown values for Supplier status
    this.serviceStatusService.getAllList().then((statuses: Servicestatus[]) => {
      this.serviceStatuses = statuses;
    });

    // Fetch dropdown values for Service category
    this.serviceCategoryService .getAllList().then((categories: Servicecategory[]) => {
      this.serviceCategories = categories;
    });

    // Load regex validations
    this.regexService.get('service').then((regs: []) => {
      this.regexes = regs;
      this.createForm();
    });

  }

  createView() {
    this.imageurl = 'assets/pending.gif';
    this.loadTable("");
  }

  enableButtons(add:boolean, upd:boolean, del:boolean){
    this.enaadd=add;
    this.enaupd=upd;
    this.enadel=del;
  }

  // Determine button access based on user roles
  buttonStates(authorities: { module: string; operation: string }[]): void {
    this.hasInsertAuthority = authorities.some(authority => authority.module === 'service' && authority.operation === 'insert');
    this.hasUpdateAuthority = authorities.some(authority => authority.module === 'service' && authority.operation === 'update');
    this.hasDeleteAuthority = authorities.some(authority => authority.module === 'service' && authority.operation === 'delete');
  }


  //Add a service
  add(){
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

      this.service = this.form.getRawValue();

      let servicedata: string = "";

      servicedata = servicedata + "<br>Number is : " + this.service.code;
      servicedata = servicedata + "<br>Name is : " + this.service.name;

      const confirm = this.matdialog.open(ConfirmComponent, {
        width: '500px',
        data: {
          heading: "Confirmation - Service Add",
          message: "Are you sure to Add the following Service? <br> <br>" + servicedata
        }
      });

      let addstatus: boolean = false;
      let addmessage: string = "Server Not Found";

      confirm.afterClosed().subscribe(async result => {
        if (result) {
          this.serviceService.add(this.service).then((responce: [] | undefined) => {
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
              data: {heading: "Status -Supplier Add", message: addmessage}
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

  //Update a Service
  async update(): Promise<void> {

    const errors = this.getErrors();

    if (errors !== "") {
      this.matdialog.open(MessageComponent, {
        width: '500px',
        data: {
          heading: "Errors - Service Update",
          message: "You have the following errors:<br>" + errors
        }
      });

      return;
    }

    const updates = this.getUpdates();

    if (updates === "") {
      this.matdialog.open(MessageComponent, {
        width: '500px',
        data: {
          heading: "Service Update",
          message: "Nothing Changed"
        }
      });

      return;
    }

    const confirmDialog = this.matdialog.open(ConfirmComponent, {
      width: '500px',
      data: {
        heading: "Confirmation - Service Update",
        message:
          "Are you sure you want to save the following updates?<br><br>" +
          updates
      }
    });

    const confirmed = await confirmDialog.afterClosed().toPromise();

    if (!confirmed) {
      return;
    }

    try {
      const updatedService: Service = this.form.getRawValue();
      updatedService.id = this.oldservice.id;

      const response: any =
        await this.serviceService.update(updatedService);

      /*
       * Consider the operation successful when:
       * 1. No response body is returned, or
       * 2. There is no errors property, or
       * 3. errors is an empty string.
       */
      if (
        response == null ||
        response.errors == null ||
        response.errors === ""
      ) {
        this.matdialog.open(MessageComponent, {
          width: '500px',
          data: {
            heading: "Status - Service Update",
            message: "Successfully Updated"
          }
        });

        this.form.reset();
        this.selectedrow = null;

        Object.values(this.form.controls).forEach(control => {
          control.markAsUntouched();
          control.markAsPristine();
        });

        this.enableButtons(true, false, false);
        this.loadTable("");

      } else {
        this.matdialog.open(MessageComponent, {
          width: '500px',
          data: {
            heading: "Service Update Failed",
            message: response.errors
          }
        });
      }

    } catch (error: any) {
      console.error("Service update error:", error);

      this.matdialog.open(MessageComponent, {
        width: '500px',
        data: {
          heading: "Service Update Failed",
          message:
            error?.error?.message ||
            error?.message ||
            "Unable to update the service"
        }
      });
    }
  }



  //Delete a Service
  delete(){

    const confirm = this.matdialog.open(ConfirmComponent, {
      width: '500px',
      data: {
        heading: "Confirmation - Service Delete",
        message: "Are you sure to Delete following Service? <br> <br>" + this.service.name
      }
    });

    confirm.afterClosed().subscribe(async result => {
      if (result) {
        let delstatus: boolean = false;
        let delmessage: string = "Server Not Found";

        this.serviceService.delete(this.service.id)
          .then((responce: [] | undefined) => {
            if (responce != undefined) { // @ts-ignore
              delstatus = responce['errors'] == "";
              if (!delstatus) { // @ts-ignore
                delmessage = responce['errors'];
              }
            } else {
              delstatus = false;
              delmessage = "Content Not Found"
            }
          })
          .finally(() => {
            if (delstatus) {
              delmessage = "Successfully Deleted";
              this.form.reset();
              // this.clearImage();
              Object.values(this.form.controls).forEach(control => { control.markAsTouched(); });
              this.loadTable("");
            }

            const stsmsg = this.matdialog.open(MessageComponent, {
              width: '500px',
              data: {heading: "Status - Service Delete ", message: delmessage}
            });
            stsmsg.afterClosed().subscribe(async result => { if (!result) { return; } });

          });
      }
    });

  }

  clear(){
    const confirm = this.matdialog.open(ConfirmComponent, {
      width: '500px',
      data: {
        heading: "Confirmation - Service data clear",
        message: "Are you sure to Clear following Details ? <br> <br>" + this.service.name
      }
    });

    confirm.afterClosed().subscribe(async result => {
      if (result) {
        this.form.reset();
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

  // Assign regex validations dynamically and detect field changes
  createForm() {

    // Assign regex validations dynamically
    this.form.controls['code']
      .setValidators([
        Validators.required,
        Validators.pattern(this.regexes['code']['regex'])
      ]);


    this.form.controls['name']
      .setValidators([
        Validators.required,
        Validators.pattern(this.regexes['name']['regex'])
      ]);

    this.form.controls['duration']
      .setValidators([
        Validators.required,
        Validators.min(5)
        // Validators.pattern(this.regexes['duration']['regex'])
      ]);

    this.form.controls['price']
      .setValidators([
        Validators.required,
        Validators.min(50)
        // Validators.pattern(this.regexes['price']['regex'])

      ]);


    this.form.controls['servicecategory']
      .setValidators([
        Validators.required
      ]);


    this.form.controls['servicestatus']
      .setValidators([
        Validators.required
      ]);


    // Refresh validation status after dynamic validators
    Object.keys(this.form.controls).forEach(controlName => {
      // this.form.controls[controlName].updateValueAndValidity();
    });


    // Detect field changes
    for (const controlName in this.form.controls) {

      const control = this.form.controls[controlName];

      control.valueChanges.subscribe(value => {


      });

    }


    Object.values(this.form.controls).forEach(control => {
      control.markAsTouched();
    });


    this.enableButtons(true, false, false);

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

  // Fill form fields with selected Supplier data
  fillForm(service: Service) {


    this.selectedrow = service;

    this.service = JSON.parse(JSON.stringify(service));
    this.oldservice = JSON.parse(JSON.stringify(service));

    this.service.servicestatus =
      this.serviceStatuses.find(
        status => status.id === this.service.servicestatus.id
      )!;

    this.service.servicecategory =
      this.serviceCategories.find(
        category => category.id === this.service.servicecategory.id
      )!;

    this.form.patchValue(this.service);
    this.form.markAsPristine();

    this.enableButtons(false, true, true);

  }

  // -------------------------------------------------------------------

  // Execute short search by criteria
  btnSearchMc(): void {

    const sserchdata = this.serviceSearchForm.getRawValue();

    let code = sserchdata.srcode;
    let name = sserchdata.srname;
    // let mobile = sserchdata.ssmobile;

    let query = "";

    if (code != null && code.trim() != "")
      query = query + "&code=" + code;

    if (name != null && name.trim() != "")
      query = query + "&name=" + name;

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
        this.serviceSearchForm.reset();
        this.loadTable("");
      }
    });

  }

  // Fetch Supplier records and load into the table
  loadTable(query: string) {
    this.serviceService.getAll(query)
      .then((src: Service[]) => {
        this.services = src;
        this.imageurl = 'assets/fullfilled.png';
        // this.numberService.setLastSequenceNumber(this.suppliers[this.suppliers.length-1].registernumber);
        // this.generateNumber();
      })
      .catch((error) => {
        console.log(error);
        this.imageurl = 'assets/rejected.png';
      })
      .finally(() => {
        this.data = new MatTableDataSource(this.services);
        this.data.paginator = this.paginator;
      });

  }

// -------------------------------------------------------------------

  filterTable(): void {

    const srfilterdata = this.serviceFilterForm.getRawValue();

    this.data.filterPredicate = (servicec: Service, filter: string) => {
      return (srfilterdata.code == null || servicec.code.includes(srfilterdata.cscode)) &&
        (srfilterdata.name == null || servicec.name.toLowerCase().includes(srfilterdata.csname)) &&
        (srfilterdata.duration == null || servicec.duration.toString().includes(srfilterdata.csaddress)) &&
        (srfilterdata.price == null || servicec.price.toString().includes(srfilterdata.csdescription)) ;
    };

    this.data.filter = 'xx';

  }





}
