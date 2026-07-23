import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {MatDialog} from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import {Customer} from '../../../entity/customer';
import {Service} from '../../../entity/service';
import {Employee} from '../../../entity/employee';
import {CustomerService} from '../../../service/customerservice';
import {ServiceService} from '../../../service/serviceService';
import {AppointmentService} from '../../../service/appointmentservice';
import {Appointment, AppointmentCreateRequest} from '../../../entity/appointment';
import {MessageComponent} from '../../../util/dialog/message/message.component';
import {ConfirmComponent} from '../../../util/dialog/confirm/confirm.component';
import {AuthorizationManager} from '../../../service/authorizationmanager';


interface BookingLine {
  service: Service;
  employee: Employee | null;
  startTime: string;
  endTime: string;
  agreedPrice: number;
  availableEmployees: Employee[];
  loading: boolean;
}

@Component({
  selector: 'app-appointment',
  templateUrl: './appointment.component.html',
  styleUrls: ['./appointment.component.css']
})
export class AppointmentComponent implements OnInit {
  phoneForm: FormGroup;
  appointmentForm: FormGroup;

  customers: Customer[] = [];
  services: Service[] = [];
  selectedCustomer: Customer | null = null;
  selectedServices: Service[] = [];
  bookingLines: BookingLine[] = [];
  // appointments: Appointment[] = [];

  selectedTab = 0;
  searchingCustomers = false;
  loadingServices = false;
  saving = false;
  customerSearchPerformed = false;
  hasInsertAuthority = false;
  minDate = new Date();

  appointments = new MatTableDataSource<Appointment>([]);

  displayedColumns: string[] = [
    'customer',
    'appointmentDate',
    'appointmentTime',
    'status',
    'actions'
  ];

  editingAppointment: Appointment | null = null;
  isEditMode = false;

  constructor(
    private fb: FormBuilder,
    private customerService: CustomerService,
    private serviceService: ServiceService,
    private appointmentService: AppointmentService,
    private dialog: MatDialog,
    public authService: AuthorizationManager
  ) {
    this.phoneForm = this.fb.group({
      mobile: new FormControl('', [
        Validators.required,
        Validators.pattern(/^0\d{9}$/)
      ])
    });

    this.appointmentForm = this.fb.group({
      appointmentDate: new FormControl('', Validators.required),
      appointmentTime: new FormControl('', Validators.required),
      description: new FormControl('')
    });
  }

  async ngOnInit(): Promise<void> {
    const array = this.authService.getAuthorities();
    if (Array.isArray(array)) {
      const authorities = this.authService.extractAuthorities(array);
      this.hasInsertAuthority = authorities.some(
        (a: {module: string; operation: string}) =>
          a.module.toLowerCase() === 'appointment' && a.operation.toLowerCase() === 'insert'
      );
    }
    await this.loadServices();
    await this.loadAppointments();
  }

  async loadServices(): Promise<void> {
    this.loadingServices = true;
    try {
      this.services = await this.serviceService.getAll('');
    } catch (error) {
      this.showMessage('Appointment', 'Unable to load salon services.');
    } finally {
      this.loadingServices = false;
    }
  }

  async loadAppointments(): Promise<void> {
    try{
      const appointmentList = await this.appointmentService.getAll();
      this.appointments.data = appointmentList;
    }catch (error) {
      console.error('Failed to load appointments:', error);
      this.appointments.data = [];
    }
  }

  async searchCustomers(): Promise<void> {
    this.phoneForm.markAllAsTouched();
    if (this.phoneForm.invalid) return;

    this.searchingCustomers = true;
    this.customerSearchPerformed = false;
    this.selectedCustomer = null;
    try {
      const mobile = this.phoneForm.controls['mobile'].value;
      this.customers = await this.customerService.getAll(
        `?mobile=${encodeURIComponent(mobile)}`
      );
      this.customerSearchPerformed = true;
    } catch (error) {
      this.customers = [];
      this.showMessage('Customer Search', 'Unable to search customers.');
    } finally {
      this.searchingCustomers = false;
    }
  }

  selectCustomer(customer: Customer): void {
    this.selectedCustomer = customer;
  }

  continueFromCustomer(): void {
    if (!this.selectedCustomer) {
      this.showMessage('Customer Required', 'Select a customer before continuing.');
      return;
    }
    this.selectedTab = 1;
  }

  toggleService(service: Service, checked: boolean): void {
    if (checked && !this.selectedServices.some(s => s.id === service.id)) {
      this.selectedServices = [...this.selectedServices, service];
    } else if (!checked) {
      this.selectedServices = this.selectedServices.filter(s => s.id !== service.id);
    }
  }

  isServiceSelected(service: Service): boolean {
    return this.selectedServices.some(s => s.id === service.id);
  }

  continueFromServices(): void {
    if (this.selectedServices.length === 0) {
      this.showMessage('Services Required', 'Select at least one salon service.');
      return;
    }
    this.selectedTab = 2;
  }

  async buildSchedule(): Promise<void> {
    this.appointmentForm.markAllAsTouched();
    if (this.appointmentForm.invalid) return;

    let cursor = this.withSeconds(this.appointmentForm.controls['appointmentTime'].value);
    this.bookingLines = [];

    for (const service of this.selectedServices) {
      const startTime = cursor;
      const endTime = this.addMinutes(startTime, service.duration);
      const line: BookingLine = {
        service,
        employee: null,
        availableEmployees: [],
        startTime,
        endTime,
        loading: true,
        agreedPrice: Number(service.price)
      };
      this.bookingLines.push(line);
      cursor = endTime;

      try {
        line.availableEmployees = await this.appointmentService.getAvailableEmployees(
          service.id,
          this.dateValue,
          startTime
        );
      } catch (error) {
        line.availableEmployees = [];
      } finally {
        line.loading = false;
      }
    }
  }

  employeeChanged(line: BookingLine, employee: Employee | null): void {
    line.employee = employee;
  }

  goToReview(): void {
    if (this.bookingLines.length !== this.selectedServices.length) {
      this.showMessage('Schedule Required', 'Check the schedule before continuing.');
      return;
    }
    this.selectedTab = 3;
  }

  // Save the Appointment details
  async save(): Promise<void> {
    if (!this.selectedCustomer || this.bookingLines.length === 0 || this.saving) return;

    const pending = this.bookingLines.filter(line => !line.employee).length;
    const summary = `${this.selectedCustomer.callingname}<br>` +
      `${this.dateValue} at ${this.timeValue}<br>` +
      `${this.selectedServices.length} service(s), ${pending} pending assignment(s)<br>` +
      `Estimated cost: ${this.estimatedCost.toFixed(2)}`;

    const ref = this.dialog.open(ConfirmComponent, {
      width: '500px',
      data: {heading: 'Confirm Appointment', message: summary}
    });

    ref.afterClosed().subscribe(async confirmed => {
      if (!confirmed || !this.selectedCustomer) return;
      this.saving = true;
      const request: AppointmentCreateRequest = {
        appointmentDate: this.dateValue,
        appointmentTime: this.withSeconds(this.timeValue),
        description: this.appointmentForm.controls['description'].value || '',
        customerId: this.selectedCustomer.id,
        services: this.bookingLines.map(line => ({
          serviceId: line.service.id,
          employeeId: line.employee?.id ?? null
        }))
      };

      try {
        const response = await this.appointmentService.add(request);
        if (response.errors) {
          this.showMessage('Appointment Not Saved', response.errors);
        } else {
          this.showMessage('Appointment', 'Successfully saved.');
          await this.loadAppointments();
          this.reset();
        }
      } catch (error) {
        this.showMessage('Appointment Not Saved', 'The server could not save the appointment.');
      } finally {
        this.saving = false;
      }
    });
  }

  // Edit the selected Appointment details
  async editAppointment(appointment: Appointment): Promise<void> {

    if (!appointment.id) {
      this.showMessage(
        'Appointment',
        'The selected appointment does not have an ID.'
      );
      return;
    }

    try {
      const fullAppointment =
        await this.appointmentService.getById(appointment.id);

      if (!fullAppointment) {
        this.showMessage(
          'Appointment',
          'Unable to load appointment details.'
        );
        return;
      }

      this.editingAppointment = fullAppointment;
      this.isEditMode = true;

      // Tab 1: Customer
      this.selectedCustomer = fullAppointment.customer;

      const serviceLines =
        fullAppointment.appointmentservices ?? [];

      if (serviceLines.length === 0) {
        this.showMessage(
          'Appointment',
          'No services were returned for this appointment.'
        );
        return;
      }

      // Tab 2: Select all related services
      this.selectedServices = serviceLines.map(
        line => line.service
      );

      // Tab 3 and Review tab
      this.bookingLines = serviceLines.map(line => ({
        id: line.id,
        service: line.service,
        employee: line.employee ?? null,
        startTime: line.startTime,
        endTime: line.endTime,
        agreedPrice: Number(line.agreedPrice),
        availableEmployees: line.employee
          ? [line.employee]
          : [],
        loading: false
      }));

      this.appointmentForm.patchValue({
        appointmentDate: fullAppointment.appointmentDate,
        appointmentTime:
          fullAppointment.appointmentTime?.substring(0, 5),
        description: fullAppointment.description
      });

      this.selectedTab = 1;

      window.scrollTo({
        top: 0,
        behavior: 'smooth'
      });
    } catch (error) {
      console.error('Unable to load appointment:', error);

      this.showMessage(
        'Appointment',
        'Unable to load the selected appointment details.'
      );
    }
  }

  // Tab 1: Customer
  // this.selectedCustomer = appointment.customer;
  // this.phoneForm.patchValue({
  //                             mobile: this.selectedCustomer.mobile
  //                           })


  reset(): void {
    this.phoneForm.reset();
    this.appointmentForm.reset();
    this.customers = [];
    this.selectedCustomer = null;
    this.selectedServices = [];
    this.bookingLines = [];
    this.customerSearchPerformed = false;
    this.selectedTab = 0;
  }

  get totalDuration(): number {
    return this.selectedServices.reduce((sum, service) => sum + service.duration, 0);
  }

  get estimatedCost(): number {
    return this.selectedServices.reduce((sum, service) => sum + Number(service.price), 0);
  }

  get dateValue(): string {
    const value = this.appointmentForm.controls['appointmentDate'].value;
    if (!value) return '';
    if (typeof value === 'string') return value.substring(0, 10);
    const year = value.getFullYear();
    const month = String(value.getMonth() + 1).padStart(2, '0');
    const day = String(value.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  }

  get timeValue(): string {
    return this.appointmentForm.controls['appointmentTime'].value || '';
  }

  private addMinutes(time: string, minutes: number): string {
    const [hours, mins] = time.split(':').map(Number);
    const total = hours * 60 + mins + minutes;
    const resultHours = Math.floor(total / 60) % 24;
    const resultMinutes = total % 60;
    return `${String(resultHours).padStart(2, '0')}:${String(resultMinutes).padStart(2, '0')}:00`;
  }

  private withSeconds(time: string): string {
    return time.length === 5 ? `${time}:00` : time;
  }

  private showMessage(heading: string, message: string): void {
    this.dialog.open(MessageComponent, {
      width: '500px',
      data: {heading, message}
    });
  }
}

