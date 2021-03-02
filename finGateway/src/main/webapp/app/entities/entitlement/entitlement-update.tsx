import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './entitlement.reducer';
import { IEntitlement } from 'app/shared/model/entitlement.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEntitlementUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EntitlementUpdate = (props: IEntitlementUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { entitlementEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/entitlement');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...entitlementEntity,
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
          <h2 id="finGatewayApp.entitlement.home.createOrEditLabel">
            <Translate contentKey="finGatewayApp.entitlement.home.createOrEditLabel">Create or edit a Entitlement</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : entitlementEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="entitlement-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="entitlement-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="workDayNoLabel" for="entitlement-workDayNo">
                  <Translate contentKey="finGatewayApp.entitlement.workDayNo">Work Day No</Translate>
                </Label>
                <AvField
                  id="entitlement-workDayNo"
                  type="text"
                  name="workDayNo"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="medicalReimbursementLabel" for="entitlement-medicalReimbursement">
                  <Translate contentKey="finGatewayApp.entitlement.medicalReimbursement">Medical Reimbursement</Translate>
                </Label>
                <AvField
                  id="entitlement-medicalReimbursement"
                  type="text"
                  name="medicalReimbursement"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    min: { value: 0, errorMessage: translate('entity.validation.min', { min: 0 }) },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="vaccineReimbursementLabel" for="entitlement-vaccineReimbursement">
                  <Translate contentKey="finGatewayApp.entitlement.vaccineReimbursement">Vaccine Reimbursement</Translate>
                </Label>
                <AvField
                  id="entitlement-vaccineReimbursement"
                  type="text"
                  name="vaccineReimbursement"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    min: { value: 0, errorMessage: translate('entity.validation.min', { min: 0 }) },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="vitaminsReimbursementLabel" for="entitlement-vitaminsReimbursement">
                  <Translate contentKey="finGatewayApp.entitlement.vitaminsReimbursement">Vitamins Reimbursement</Translate>
                </Label>
                <AvField
                  id="entitlement-vitaminsReimbursement"
                  type="text"
                  name="vitaminsReimbursement"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    min: { value: 0, errorMessage: translate('entity.validation.min', { min: 0 }) },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="opticalReimbursementLabel" for="entitlement-opticalReimbursement">
                  <Translate contentKey="finGatewayApp.entitlement.opticalReimbursement">Optical Reimbursement</Translate>
                </Label>
                <AvField
                  id="entitlement-opticalReimbursement"
                  type="text"
                  name="opticalReimbursement"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    min: { value: 0, errorMessage: translate('entity.validation.min', { min: 0 }) },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/entitlement" replace color="info">
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
  entitlementEntity: storeState.entitlement.entity,
  loading: storeState.entitlement.loading,
  updating: storeState.entitlement.updating,
  updateSuccess: storeState.entitlement.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EntitlementUpdate);
