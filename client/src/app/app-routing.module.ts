import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./view/login/login.component";
import {MainwindowComponent} from "./view/mainwindow/mainwindow.component";
import {EmployeeComponent} from "./view/modules/employee/employee.component";
import {HomeComponent} from "./view/home/home.component";
import {UserComponent} from "./view/modules/user/user.component";
import {PrivilageComponent} from "./view/modules/privilage/privilage.component";
import {OperationComponent} from "./view/modules/operation/operation.component";
import {CustomerComponent} from "./view/modules/customer/customer.component";
import {PurchaseorderComponent} from "./view/modules/purchaseorder/purchaseorder.component";
import {SupplierComponent} from "./view/modules/supplier/supplier.component";
import {ServiceComponent} from "./view/modules/service/service.component";
import {InvoiceComponent} from "./view/modules/invoice/invoice.component";
import {AppointmentComponent} from "./view/modules/appointment/appointment.component";
import {PromotionComponent} from "./view/modules/promotion/promotion.component";
import {PaymentComponent} from "./view/modules/payment/payment.component";
import {CustomerfeedbackComponent} from "./view/modules/customerfeedback/customerfeedback.component";
import {ProductComponent} from "./view/modules/product/product.component";
import {InventoryComponent} from "./view/modules/inventory/inventory.component";
import {StocktransferComponent} from "./view/modules/stocktransfer/stocktransfer.component";
import {GoodreceivednoteComponent} from "./view/modules/goodreceivednote/goodreceivednote.component";
import {ProductsaleinvoiceComponent} from "./view/modules/productsaleinvoice/productsaleinvoice.component";

import { CountByDesignationComponent } from './report/view/count-by-designation/count-by-designation.component';
import { CountByAppointmentStatusComponent } from './report/view/count-by-appointment-status/count-by-appointment-status.component';
import { CountByCustomerTypeComponent } from './report/view/count-by-customer-type/count-by-customer-type.component';
import { CountByEmpStatusComponent } from './report/view/count-by-emp-status/count-by-emp-status.component';
import { CountByGenderComponent } from './report/view/count-by-gender/count-by-gender.component';
import { CountByItemCategoryComponent } from './report/view/count-by-item-category/count-by-item-category.component';
import { CountByServiceCategoryComponent } from './report/view/count-by-service-category/count-by-service-category.component';
import { RevenueByMonthComponent } from './report/view/revenue-by-month/revenue-by-month.component';
import { RevenueByPaymentMethodComponent } from './report/view/revenue-by-payment-method/revenue-by-payment-method.component';
import { TotalByPoSupplierComponent } from './report/view/total-by-po-supplier/total-by-po-supplier.component';


const routes: Routes = [
  {path: "login", component: LoginComponent},
  {path: "", redirectTo: 'login', pathMatch: 'full'},
  {
    path: "main",
    component: MainwindowComponent,
    children: [
      {path: "home", component: HomeComponent},
      {path: "employee", component: EmployeeComponent},
      {path: "operation", component: OperationComponent},
      {path: "user", component: UserComponent},
      {path: "privilege", component: PrivilageComponent},
      {path: "customer", component: CustomerComponent},
      {path: "supplier", component: SupplierComponent},
      {path: "service", component: ServiceComponent},
      {path: "promotion", component: PromotionComponent},
      {path: "appointment", component: AppointmentComponent},
      {path: "invoice", component: InvoiceComponent},
      {path: "payment", component: PaymentComponent},
      {path: "payment", component: PaymentComponent},
      {path: "purchaseorder", component: PurchaseorderComponent},
      {path: "customerfeedback", component: CustomerfeedbackComponent},
      {path: "product", component: ProductComponent},
      {path: "Inventory", component: InventoryComponent},
      {path: "stocktransfer", component: StocktransferComponent},
      {path: "goodreceivednote", component: GoodreceivednoteComponent},
      {path: "productsaleinvoice", component: ProductsaleinvoiceComponent},

      {path:"reports/countbydesignation", component: CountByDesignationComponent},
      {path:"reports/countbyappointmentstatus", component: CountByAppointmentStatusComponent},
      {path:"reports/countbycustomertype", component: CountByCustomerTypeComponent},
      {path:"reports/countbyempstatus", component: CountByEmpStatusComponent},
      {path:"reports/countbygender", component: CountByGenderComponent},
      {path:"reports/countbyitemcategory", component: CountByItemCategoryComponent},
      {path:"reports/countbyservicecategory", component: CountByServiceCategoryComponent},
      {path:"reports/revenuebymonth", component: RevenueByMonthComponent},
      {path:"reports/revenuebypaymentmethod", component: RevenueByPaymentMethodComponent},
      {path:"reports/totalbyposupplier", component: TotalByPoSupplierComponent},
    ]
  }];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
