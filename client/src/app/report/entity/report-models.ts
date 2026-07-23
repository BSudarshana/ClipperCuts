export class CountByDesignation {
  public designation!: string;
  public count!: number;
  public percentage!: number;
}

export class CountByEmpStatus {
  public status!: string;
  public count!: number;
  public percentage!: number;
}

export class CountByCustomerType {
  public type!: string;
  public count!: number;
  public percentage!: number;
}

export class CountByGender {
  public gender!: string;
  public count!: number;
  public percentage!: number;
}

export class CountByAppointmentStatus {
  public status!: string;
  public count!: number;
  public percentage!: number;
}

export class RevenueByPaymentMethod {
  public method!: string;
  public totalAmount!: number;
  public percentage!: number;
}

export class CountByItemCategory {
  public category!: string;
  public count!: number;
  public percentage!: number;
}

export class CountByServiceCategory {
  public category!: string;
  public count!: number;
  public percentage!: number;
}

export class TotalByPoSupplier {
  public supplier!: string;
  public totalAmount!: number;
  public percentage!: number;
}

export class RevenueByMonth {
  public year!: number;
  public month!: number;
  public totalAmount!: number;
}
