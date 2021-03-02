import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label, UncontrolledTooltip } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IEntitlement } from 'app/shared/model/entitlement.model';
import { getEntities as getEntitlements } from 'app/entities/entitlement/entitlement.reducer';
import { getEntity, updateEntity, createEntity, reset } from './employee.reducer';
import { IEmployee } from 'app/shared/model/employee.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEmployeeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmployeeUpdate = (props: IEmployeeUpdateProps) => {
  const [entitlementId, setEntitlementId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { employeeEntity, entitlements, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/employee');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getEntitlements();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...employeeEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="finGatewayApp.employee.home.createOrEditLabel">
            <Translate contentKey="finGatewayApp.employee.home.createOrEditLabel">Create or edit a Employee</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : employeeEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="employee-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="employee-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="workDayNoLabel" for="employee-workDayNo">
                  <Translate contentKey="finGatewayApp.employee.workDayNo">Work Day No</Translate>
                </Label>
                <AvField
                  id="employee-workDayNo"
                  type="text"
                  name="workDayNo"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
                <UncontrolledTooltip target="workDayNoLabel">
                  <Translate contentKey="finGatewayApp.employee.help.workDayNo" />
                </UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="usernameLabel" for="employee-username">
                  <Translate contentKey="finGatewayApp.employee.username">Username</Translate>
                </Label>
                <AvField
                  id="employee-username"
                  type="text"
                  name="username"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
                <UncontrolledTooltip target="usernameLabel">
                  <Translate contentKey="finGatewayApp.employee.help.username" />
                </UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="designationLabel" for="employee-designation">
                  <Translate contentKey="finGatewayApp.employee.designation">Designation</Translate>
                </Label>
                <AvField id="employee-designation" type="text" name="designation" />
                <UncontrolledTooltip target="designationLabel">
                  <Translate contentKey="finGatewayApp.employee.help.designation" />
                </UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="lastNameLabel" for="employee-lastName">
                  <Translate contentKey="finGatewayApp.employee.lastName">Last Name</Translate>
                </Label>
                <AvField
                  id="employee-lastName"
                  type="text"
                  name="lastName"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
                <UncontrolledTooltip target="lastNameLabel">
                  <Translate contentKey="finGatewayApp.employee.help.lastName" />
                </UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="firstNameLabel" for="employee-firstName">
                  <Translate contentKey="finGatewayApp.employee.firstName">First Name</Translate>
                </Label>
                <AvField
                  id="employee-firstName"
                  type="text"
                  name="firstName"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
                <UncontrolledTooltip target="firstNameLabel">
                  <Translate contentKey="finGatewayApp.employee.help.firstName" />
                </UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label for="employee-entitlement">
                  <Translate contentKey="finGatewayApp.employee.entitlement">Entitlement</Translate>
                </Label>
                <AvInput id="employee-entitlement" type="select" className="form-control" name="entitlement.id">
                  <option value="" key="0" />
                  {entitlements
                    ? entitlements.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/employee" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  entitlements: storeState.entitlement.entities,
  employeeEntity: storeState.employee.entity,
  loading: storeState.employee.loading,
  updating: storeState.employee.updating,
  updateSuccess: storeState.employee.updateSuccess,
});

const mapDispatchToProps = {
  getEntitlements,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmployeeUpdate);
