import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './entitlement.reducer';
import { IEntitlement } from 'app/shared/model/entitlement.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEntitlementProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Entitlement = (props: IEntitlementProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { entitlementList, match, loading } = props;
  return (
    <div>
      <h2 id="entitlement-heading">
        <Translate contentKey="finGatewayApp.entitlement.home.title">Entitlements</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="finGatewayApp.entitlement.home.createLabel">Create new Entitlement</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {entitlementList && entitlementList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="finGatewayApp.entitlement.workDayNo">Work Day No</Translate>
                </th>
                <th>
                  <Translate contentKey="finGatewayApp.entitlement.medicalReimbursement">Medical Reimbursement</Translate>
                </th>
                <th>
                  <Translate contentKey="finGatewayApp.entitlement.vaccineReimbursement">Vaccine Reimbursement</Translate>
                </th>
                <th>
                  <Translate contentKey="finGatewayApp.entitlement.vitaminsReimbursement">Vitamins Reimbursement</Translate>
                </th>
                <th>
                  <Translate contentKey="finGatewayApp.entitlement.opticalReimbursement">Optical Reimbursement</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {entitlementList.map((entitlement, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${entitlement.id}`} color="link" size="sm">
                      {entitlement.id}
                    </Button>
                  </td>
                  <td>{entitlement.workDayNo}</td>
                  <td>{entitlement.medicalReimbursement}</td>
                  <td>{entitlement.vaccineReimbursement}</td>
                  <td>{entitlement.vitaminsReimbursement}</td>
                  <td>{entitlement.opticalReimbursement}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${entitlement.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${entitlement.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${entitlement.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="finGatewayApp.entitlement.home.notFound">No Entitlements found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ entitlement }: IRootState) => ({
  entitlementList: entitlement.entities,
  loading: entitlement.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Entitlement);
