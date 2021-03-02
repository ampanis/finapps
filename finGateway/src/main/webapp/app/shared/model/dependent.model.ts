import { IEmployee } from 'app/shared/model/employee.model';
import { RelationshipType } from 'app/shared/model/enumerations/relationship-type.model';

export interface IDependent {
  id?: number;
  firstName?: string;
  lastName?: string;
  relationshipx?: RelationshipType;
  relationshipOthers?: string;
  imageContentType?: string;
  image?: any;
  employee?: IEmployee;
}

export const defaultValue: Readonly<IDependent> = {};
