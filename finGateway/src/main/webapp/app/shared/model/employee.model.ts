import { IEntitlement } from 'app/shared/model/entitlement.model';
import { IDependent } from 'app/shared/model/dependent.model';

export interface IEmployee {
  id?: number;
  workDayNo?: string;
  username?: string;
  designation?: string;
  lastName?: string;
  firstName?: string;
  entitlement?: IEntitlement;
  dependents?: IDependent[];
}

export const defaultValue: Readonly<IEmployee> = {};
