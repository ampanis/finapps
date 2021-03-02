export interface IEntitlement {
  id?: number;
  workDayNo?: string;
  medicalReimbursement?: number;
  vaccineReimbursement?: number;
  vitaminsReimbursement?: number;
  opticalReimbursement?: number;
}

export const defaultValue: Readonly<IEntitlement> = {};
