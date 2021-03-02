import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './employee.reducer';
import { IEmployee } from 'app/shared/model/employee.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmployeeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmployeeDetail = (props: IEmployeeDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { employeeEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="finGatewayApp.employee.detail.title">Employee</Translate> [<b>{employeeEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="workDayNo">
              <Translate contentKey="finGatewayApp.employee.workDayNo">Work Day No</Translate>
            </span>
            <UncontrolledTooltip target="workDayNo">
              <Translate contentKey="finGatewayApp.employee.help.workDayNo" />
            </UncontrolledTooltip>
          </dt>
          <dd>{employeeEntity.workDayNo}</dd>
          <dt>
            <span id="username">
              <Translate contentKey="finGatewayApp.employee.username">Username</Translate>
            </span>
            <UncontrolledTooltip target="username">
              <Translate contentKey="finGatewayApp.employee.help.username" />
            </UncontrolledTooltip>
          </dt>
          <dd>{employeeEntity.username}</dd>
          <dt>
            <span id="designation">
              <Translate contentKey="finGatewayApp.employee.designation">Designation</Translate>
            </span>
            <UncontrolledTooltip target="designation">
              <Translate contentKey="finGatewayApp.employee.help.designation" />
            </UncontrolledTooltip>
          </dt>
          <dd>{employeeEntity.designation}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="finGatewayApp.employee.lastName">Last Name</Translate>
            </span>
            <UncontrolledTooltip target="lastName">
              <Translate contentKey="finGatewayApp.employee.help.lastName" />
            </UncontrolledTooltip>
          </dt>
          <dd>{employeeEntity.lastName}</dd>
          <dt>
            <span id="firstName">
              <Translate contentKey="finGatewayApp.employee.firstName">First Name</Translate>
            </span>
            <UncontrolledTooltip target="firstName">
              <Translate contentKey="finGatewayApp.employee.help.firstName" />
            </UncontrolledTooltip>
          </dt>
          <dd>{employeeEntity.firstName}</dd>
          <dt>
            <Translate contentKey="finGatewayApp.employee.entitlement">Entitlement</Translate>
          </dt>
          <dd>{employeeEntity.entitlement ? employeeEntity.entitlement.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/employee" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employee/${employeeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ employee }: IRootState) => ({
  employeeEntity: employee.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmployeeDetail);
