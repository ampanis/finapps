import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IEmployee } from 'app/shared/model/employee.model';
import { getEntities as getEmployees } from 'app/entities/employee/employee.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './dependent.reducer';
import { IDependent } from 'app/shared/model/dependent.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDependentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const DependentUpdate = (props: IDependentUpdateProps) => {
  const [employeeId, setEmployeeId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { dependentEntity, employees, loading, updating } = props;

  const { image, imageContentType } = dependentEntity;

  const handleClose = () => {
    props.history.push('/dependent');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getEmployees();
  }, []);

  const onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => props.setBlob(name, data, contentType), isAnImage);
  };

  const clearBlob = name => () => {
    props.setBlob(name, undefined, undefined);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...dependentEntity,
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
          <h2 id="finGatewayApp.dependent.home.createOrEditLabel">
            <Translate contentKey="finGatewayApp.dependent.home.createOrEditLabel">Create or edit a Dependent</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : dependentEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="dependent-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="dependent-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="firstNameLabel" for="dependent-firstName">
                  <Translate contentKey="finGatewayApp.dependent.firstName">First Name</Translate>
                </Label>
                <AvField
                  id="dependent-firstName"
                  type="text"
                  name="firstName"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="lastNameLabel" for="dependent-lastName">
                  <Translate contentKey="finGatewayApp.dependent.lastName">Last Name</Translate>
                </Label>
                <AvField
                  id="dependent-lastName"
                  type="text"
                  name="lastName"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="relationshipxLabel" for="dependent-relationshipx">
                  <Translate contentKey="finGatewayApp.dependent.relationshipx">Relationshipx</Translate>
                </Label>
                <AvInput
                  id="dependent-relationshipx"
                  type="select"
                  className="form-control"
                  name="relationshipx"
                  value={(!isNew && dependentEntity.relationshipx) || 'CHILD'}
                >
                  <option value="CHILD">{translate('finGatewayApp.RelationshipType.CHILD')}</option>
                  <option value="GRAND_PARENT">{translate('finGatewayApp.RelationshipType.GRAND_PARENT')}</option>
                  <option value="SPOUSE">{translate('finGatewayApp.RelationshipType.SPOUSE')}</option>
                  <option value="SIBLING">{translate('finGatewayApp.RelationshipType.SIBLING')}</option>
                  <option value="PARENT">{translate('finGatewayApp.RelationshipType.PARENT')}</option>
                  <option value="OTHERS">{translate('finGatewayApp.RelationshipType.OTHERS')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="relationshipOthersLabel" for="dependent-relationshipOthers">
                  <Translate contentKey="finGatewayApp.dependent.relationshipOthers">Relationship Others</Translate>
                </Label>
                <AvField id="dependent-relationshipOthers" type="text" name="relationshipOthers" />
              </AvGroup>
              <AvGroup>
                <AvGroup>
                  <Label id="imageLabel" for="image">
                    <Translate contentKey="finGatewayApp.dependent.image">Image</Translate>
                  </Label>
                  <br />
                  {image ? (
                    <div>
                      {imageContentType ? (
                        <a onClick={openFile(imageContentType, image)}>
                          <img src={`data:${imageContentType};base64,${image}`} style={{ maxHeight: '100px' }} />
                        </a>
                      ) : null}
                      <br />
                      <Row>
                        <Col md="11">
                          <span>
                            {imageContentType}, {byteSize(image)}
                          </span>
                        </Col>
                        <Col md="1">
                          <Button color="danger" onClick={clearBlob('image')}>
                            <FontAwesomeIcon icon="times-circle" />
                          </Button>
                        </Col>
                      </Row>
                    </div>
                  ) : null}
                  <input id="file_image" type="file" onChange={onBlobChange(true, 'image')} accept="image/*" />
                  <AvInput type="hidden" name="image" value={image} />
                </AvGroup>
              </AvGroup>
              <AvGroup>
                <Label for="dependent-employee">
                  <Translate contentKey="finGatewayApp.dependent.employee">Employee</Translate>
                </Label>
                <AvInput id="dependent-employee" type="select" className="form-control" name="employee.id">
                  <option value="" key="0" />
                  {employees
                    ? employees.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/dependent" replace color="info">
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
  employees: storeState.employee.entities,
  dependentEntity: storeState.dependent.entity,
  loading: storeState.dependent.loading,
  updating: storeState.dependent.updating,
  updateSuccess: storeState.dependent.updateSuccess,
});

const mapDispatchToProps = {
  getEmployees,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DependentUpdate);
