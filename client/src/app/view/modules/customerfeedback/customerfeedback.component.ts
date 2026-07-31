import { AfterViewInit,  Component,  OnInit,  ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MatDialog} from '@angular/material/dialog';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';

import {Appointment} from '../../../entity/appointment';
import {Customer} from '../../../entity/customer';
import {Customerfeedback} from '../../../entity/customerfeedback';
import {Rating} from '../../../entity/rating';

import {AppointmentService} from '../../../service/appointmentservice';
import {CustomerFeedbackService} from '../../../service/cusfeedbackservice';
import {RatingService} from '../../../service/ratingservice';

import {ConfirmComponent} from '../../../util/dialog/confirm/confirm.component';
import {MessageComponent} from '../../../util/dialog/message/message.component';

@Component({
  selector: 'app-customerfeedback',
  templateUrl: './customerfeedback.component.html',
  styleUrls: ['./customerfeedback.component.css']
})
export class CustomerfeedbackComponent implements OnInit, AfterViewInit {

  form: FormGroup;
  searchForm: FormGroup;

  completedAppointments: Appointment[] = [];
  ratings: Rating[] = [];
  feedbackList: Customerfeedback[] = [];
  selectedFeedback: Customerfeedback | null = null;
  loading = false;

  displayedColumns: string[] = [
    'date',
    'customer',
    'appointment',
    'rating',
    'comment',
    'actions'
  ];

  dataSource = new MatTableDataSource<Customerfeedback>([]);

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(
    private formBuilder: FormBuilder,
    private customerFeedbackService: CustomerFeedbackService,
    private appointmentService: AppointmentService,
    private ratingService: RatingService,
    private dialog: MatDialog
  ) {
    this.form = this.formBuilder.group({
      appointment: [null, Validators.required],
      date: [this.today(), Validators.required],
      rating: [null, Validators.required],
      comment: ['', Validators.maxLength(1000)]
    });

    this.searchForm = this.formBuilder.group({
      customer: [''],
      rating: [null]
    });
  }

  async ngOnInit(): Promise<void> {
    await this.loadInitialData();
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
  }

  get selectedAppointment(): Appointment | null {
    return this.form.controls['appointment'].value;
  }

  get isEditMode(): boolean {
    return this.selectedFeedback?.id != null;
  }

  private async loadInitialData(): Promise<void> {
    this.loading = true;

    try {
      const [allAppointments, ratings, feedback] = await Promise.all([
        this.appointmentService.getAll(),
        this.ratingService.getAll(),
        this.customerFeedbackService.getAll()
      ]);

      this.ratings = ratings;
      this.setFeedbackList(feedback);
      this.setCompletedAppointments(allAppointments, feedback);
    } catch (error: any) {
      this.showMessage('Customer Feedback', this.errorText(error));
    } finally {
      this.loading = false;
    }
  }

  async add(): Promise<void> {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      this.showMessage(
        'Feedback Not Saved',
        'Select a completed appointment, feedback date, and rating.'
      );
      return;
    }

    const appointment = this.selectedAppointment;
    if (!appointment?.id || !appointment.customer?.id) {
      this.showMessage(
        'Feedback Not Saved',
        'The selected appointment or customer does not have an ID.'
      );
      return;
    }

    const confirmation = this.dialog.open(ConfirmComponent, {
      width: '500px',
      data: {
        heading: 'Confirmation - Add Customer Feedback',
        message: `Add feedback for ${appointment.customer.fullname}?`
      }
    });

    confirmation.afterClosed().subscribe(async result => {
      if (!result) {
        return;
      }

      try {
        const response = await this.customerFeedbackService.add(
          this.buildFeedback()
        );

        if (response?.errors) {
          this.showMessage('Feedback Not Saved', response.errors);
          return;
        }

        this.showMessage(
          'Customer Feedback',
          'Customer feedback saved successfully.'
        );
        this.clear();
        await this.refreshData();
      } catch (error: any) {
        this.showMessage('Feedback Not Saved', this.errorText(error));
      }
    });
  }

  async update(): Promise<void> {
    if (!this.selectedFeedback?.id) {
      this.showMessage(
        'Feedback Not Updated',
        'Select a customer feedback record first.'
      );
      return;
    }

    if (this.form.invalid) {
      this.form.markAllAsTouched();
      this.showMessage(
        'Feedback Not Updated',
        'Complete all required feedback fields.'
      );
      return;
    }

    const feedback: Customerfeedback = {
      ...this.buildFeedback(),
      id: this.selectedFeedback.id
    };

    const confirmation = this.dialog.open(ConfirmComponent, {
      width: '500px',
      data: {
        heading: 'Confirmation - Update Customer Feedback',
        message: 'Update the selected customer feedback record?'
      }
    });

    confirmation.afterClosed().subscribe(async result => {
      if (!result) {
        return;
      }

      try {
        const response = await this.customerFeedbackService.update(feedback);

        if (response?.errors) {
          this.showMessage('Feedback Not Updated', response.errors);
          return;
        }

        this.showMessage(
          'Customer Feedback',
          'Customer feedback updated successfully.'
        );
        this.clear();
        await this.refreshData();
      } catch (error: any) {
        this.showMessage('Feedback Not Updated', this.errorText(error));
      }
    });
  }

  delete(feedback: Customerfeedback): void {
    if (!feedback.id) {
      this.showMessage(
        'Feedback Not Deleted',
        'The selected feedback record does not have an ID.'
      );
      return;
    }

    const confirmation = this.dialog.open(ConfirmComponent, {
      width: '500px',
      data: {
        heading: 'Confirmation - Delete Customer Feedback',
        message: `Delete feedback from ${feedback.customer.fullname}?`
      }
    });

    confirmation.afterClosed().subscribe(async result => {
      if (!result) {
        return;
      }

      try {
        const response = await this.customerFeedbackService.delete(feedback.id!);

        if (response?.errors) {
          this.showMessage('Feedback Not Deleted', response.errors);
          return;
        }

        this.showMessage(
          'Customer Feedback',
          'Customer feedback deleted successfully.'
        );
        this.clear();
        await this.refreshData();
      } catch (error: any) {
        this.showMessage('Feedback Not Deleted', this.errorText(error));
      }
    });
  }

  edit(feedback: Customerfeedback): void {
    this.selectedFeedback = feedback;

    const appointment =
      this.completedAppointments.find(
        item => item.id === feedback.appointment.id
      ) ?? feedback.appointment;

    const rating =
      this.ratings.find(item => item.id === feedback.rating.id) ??
      feedback.rating;

    if (!this.completedAppointments.some(
      item => item.id === appointment.id
    )) {
      this.completedAppointments = [
        appointment,
        ...this.completedAppointments
      ];
    }

    this.form.setValue({
      appointment,
      date: feedback.date,
      rating,
      comment: feedback.comment ?? ''
    });
  }

  search(): void {
    const customerText = (
      this.searchForm.controls['customer'].value ?? ''
    ).trim().toLowerCase();

    const rating: Rating | null =
      this.searchForm.controls['rating'].value;

    this.dataSource.data = this.feedbackList.filter(feedback => {
      const customerName =
        feedback.customer?.fullname?.toLowerCase() ?? '';

      const matchesCustomer =
        !customerText || customerName.includes(customerText);

      const matchesRating =
        !rating || feedback.rating?.id === rating.id;

      return matchesCustomer && matchesRating;
    });

    this.dataSource.paginator?.firstPage();
  }

  clearSearch(): void {
    this.searchForm.reset({
      customer: '',
      rating: null
    });
    this.dataSource.data = this.feedbackList;
    this.dataSource.paginator?.firstPage();
  }

  clear(): void {
    this.selectedFeedback = null;
    this.form.reset({
      appointment: null,
      date: this.today(),
      rating: null,
      comment: ''
    });
  }

  private buildFeedback(): Customerfeedback {
    const appointment: Appointment =
      this.form.controls['appointment'].value;
    const rating: Rating = this.form.controls['rating'].value;

    return {
      date: this.form.controls['date'].value,
      comment: this.form.controls['comment'].value?.trim() ?? '',
      appointment: {
        id: appointment.id
      } as Appointment,
      customer: {
        id: appointment.customer.id
      } as Customer,
      rating: {
        id: rating.id,
        name: rating.name
      }
    };
  }

  private async refreshData(): Promise<void> {
    const [allAppointments, feedback] = await Promise.all([
      this.appointmentService.getAll(),
      this.customerFeedbackService.getAll()
    ]);

    this.setFeedbackList(feedback);
    this.setCompletedAppointments(allAppointments, feedback);
  }

  private setFeedbackList(feedback: Customerfeedback[]): void {
    this.feedbackList = feedback;
    this.dataSource.data = feedback;
    this.dataSource.paginator = this.paginator;
  }

  private setCompletedAppointments(
    appointments: Appointment[],
    feedback: Customerfeedback[]
  ): void {
    const feedbackAppointmentIds = new Set(
      feedback
        .map(item => item.appointment?.id)
        .filter(id => id != null)
    );

    this.completedAppointments = appointments.filter(appointment =>
      appointment.appointmentstatus?.name?.toLowerCase() === 'completed' &&
      !feedbackAppointmentIds.has(appointment.id)
    );
  }

  private today(): string {
    const now = new Date();
    const timezoneOffset = now.getTimezoneOffset();

    return new Date(now.getTime() - timezoneOffset * 60000)
      .toISOString()
      .substring(0, 10);
  }

  private showMessage(heading: string, message: string): void {
    this.dialog.open(MessageComponent, {
      width: '500px',
      data: {
        heading,
        message
      }
    });
  }

  private errorText(error: any): string {
    return error?.error?.errors ||
      error?.error?.message ||
      error?.message ||
      'Unable to communicate with the server.';
  }
}
