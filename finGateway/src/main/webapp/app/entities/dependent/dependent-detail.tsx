import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './dependent.reducer';
import { IDependent } from 'app/shared/model/dependent.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDependentDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const DependentDetail = (props: IDependentDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { dependentEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="finGatewayApp.dependent.detail.title">Dependent</Translate> [<b>{dependentEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="firstName">
              <Translate contentKey="finGatewayApp.dependent.firstName">First Name</Translate>
            </span>
          </dt>
          <dd>{dependentEntity.firstName}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="finGatewayApp.dependent.lastName">Last Name</Translate>
            </span>
          </dt>
          <dd>{dependentEntity.lastName}</dd>
          <dt>
            <span id="relationshipx">
              <Translate contentKey="finGatewayApp.dependent.relationshipx">Relationshipx</Translate>
            </span>
          </dt>
          <dd>{dependentEntity.relationshipx}</dd>
          <dt>
            <span id="relationshipOthers">
              <Translate contentKey="finGatewayApp.dependent.relationshipOthers">Relationship Others</Translate>
            </span>
          </dt>
          <dd>{dependentEntity.relationshipOthers}</dd>
          <dt>
            <span id="image">
              <Translate contentKey="finGatewayApp.dependent.image">Image</Translate>
            </span>
          </dt>
          <dd>
            {dependentEntity.image ? (
              <div>
                {dependentEntity.imageContentType ? (
                  <a onClick={openFile(dependentEntity.imageContentType, dependentEntity.image)}>
                    <img src={`data:${dependentEntity.imageContentType};base64,${dependentEntity.image}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {dependentEntity.imageContentType}, {byteSize(dependentEntity.image)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="finGatewayApp.dependent.employee">Employee</Translate>
          </dt>
          <dd>{dependentEntity.employee ? dependentEntity.employee.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/dependent" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/dependent/${dependentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ dependent }: IRootState) => ({
  dependentEntity: dependent.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DependentDetail);
