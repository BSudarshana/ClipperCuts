import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {MatTabsModule} from '@angular/material/tabs';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {HomeComponent} from './view/home/home.component';
import {LoginComponent} from './view/login/login.component';
import {MainwindowComponent} from './view/mainwindow/mainwindow.component';
import {EmployeeComponent} from './view/modules/employee/employee.component';
import {CustomerComponent} from './view/modules/customer/customer.component';
import {UserComponent} from './view/modules/user/user.component';
import {MatGridListModule} from "@angular/material/grid-list";
import {MatCardModule} from "@angular/material/card";
import {ReactiveFormsModule} from "@angular/forms";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatButtonModule} from "@angular/material/button";
import {MatInputModule} from "@angular/material/input";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatListModule} from "@angular/material/list";
import {MatExpansionModule} from "@angular/material/expansion";
import {MatIconModule} from "@angular/material/icon";
import {MessageComponent} from "./util/dialog/message/message.component";
import {MatDialogModule} from "@angular/material/dialog";
import {MatTableModule} from "@angular/material/table";
import {MatPaginatorModule} from "@angular/material/paginator";
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {EmployeeService} from "./service/employeeservice";
import {MatSelectModule} from "@angular/material/select";
import {ConfirmComponent} from "./util/dialog/confirm/confirm.component";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatNativeDateModule} from "@angular/material/core";
import {DatePipe} from "@angular/common";
import {MatChipsModule} from "@angular/material/chips";
import { PrivilageComponent } from './view/modules/privilage/privilage.component';
import {JwtInterceptor} from "./service/JwtInterceptor";
import {AuthorizationManager} from "./service/authorizationmanager";
import {MatSlideToggleModule} from "@angular/material/slide-toggle";
import { OperationComponent } from './view/modules/operation/operation.component';
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatRadioModule} from "@angular/material/radio";
import { SupplierComponent } from './view/modules/supplier/supplier.component';
import {PurchaseorderComponent} from "./view/modules/purchaseorder/purorder.component";
import { AppointmentComponent } from './view/modules/appointment/appointment.component';
import { ServiceComponent } from './view/modules/service/service.component';
import { PromotionComponent } from './view/modules/promotion/promotion.component';
import { CountByAppointmentStatusComponent } from './report/view/count-by-appointment-status/count-by-appointment-status.component';
import { CountByCustomerTypeComponent } from './report/view/count-by-customer-type/count-by-customer-type.component';
import { CountByDesignationComponent } from './report/view/count-by-designation/count-by-designation.component';
import { CountByEmpStatusComponent } from './report/view/count-by-emp-status/count-by-emp-status.component';
import { CountByGenderComponent } from './report/view/count-by-gender/count-by-gender.component';
import { CountByItemCategoryComponent } from './report/view/count-by-item-category/count-by-item-category.component';
import { CountByServiceCategoryComponent } from './report/view/count-by-service-category/count-by-service-category.component';
import { RevenueByMonthComponent } from './report/view/revenue-by-month/revenue-by-month.component';
import { RevenueByPaymentMethodComponent } from './report/view/revenue-by-payment-method/revenue-by-payment-method.component';
import { TotalByPoSupplierComponent } from './report/view/total-by-po-supplier/total-by-po-supplier.component';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    MainwindowComponent,
    EmployeeComponent,
    CustomerComponent,
    UserComponent,
    ConfirmComponent,
    MessageComponent,
    PrivilageComponent,
    OperationComponent,
    SupplierComponent,
    PurchaseorderComponent,
    AppointmentComponent,
    ServiceComponent,
    PromotionComponent,
    CountByDesignationComponent,
    CountByAppointmentStatusComponent,
    CountByCustomerTypeComponent,
    CountByEmpStatusComponent,
    CountByGenderComponent,
    CountByItemCategoryComponent,
    CountByServiceCategoryComponent,
    RevenueByMonthComponent,
    RevenueByPaymentMethodComponent,
    TotalByPoSupplierComponent
  ],
  imports: [
    MatDatepickerModule,
    MatNativeDateModule,
    MatSelectModule,
    MatTableModule,
    MatPaginatorModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatGridListModule,
    MatCardModule,
    MatTabsModule,
    MatFormFieldModule,
    MatButtonModule,
    MatInputModule,
    MatToolbarModule,
    MatSidenavModule,
    MatListModule,
    MatExpansionModule,
    MatIconModule,
    MatDialogModule,
    HttpClientModule,
    MatChipsModule,
    ReactiveFormsModule,
    MatSlideToggleModule,
    MatCheckboxModule,
    MatRadioModule,
    MatProgressSpinnerModule,
  ],
  providers: [
    OperationComponent,
    EmployeeService,
    DatePipe,
    AuthorizationManager,
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
