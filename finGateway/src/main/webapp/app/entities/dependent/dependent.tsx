import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { openFile, byteSize, Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './dependent.reducer';
import { IDependent } from 'app/shared/model/dependent.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDependentProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Dependent = (props: IDependentProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { dependentList, match, loading } = props;
  return (
    <div>
      <h2 id="dependent-heading">
        <Translate contentKey="finGatewayApp.dependent.home.title">Dependents</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="finGatewayApp.dependent.home.createLabel">Create new Dependent</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {dependentList && dependentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="finGatewayApp.dependent.firstName">First Name</Translate>
                </th>
                <th>
                  <Translate contentKey="finGatewayApp.dependent.lastName">Last Name</Translate>
                </th>
                <th>
                  <Translate contentKey="finGatewayApp.dependent.relationshipx">Relationshipx</Translate>
                </th>
                <th>
                  <Translate contentKey="finGatewayApp.dependent.relationshipOthers">Relationship Others</Translate>
                </th>
                <th>
                  <Translate contentKey="finGatewayApp.dependent.image">Image</Translate>
                </th>
                <th>
                  <Translate contentKey="finGatewayApp.dependent.employee">Employee</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {dependentList.map((dependent, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${dependent.id}`} color="link" size="sm">
                      {dependent.id}
                    </Button>
                  </td>
                  <td>{dependent.firstName}</td>
                  <td>{dependent.lastName}</td>
                  <td>
                    <Translate contentKey={`finGatewayApp.RelationshipType.${dependent.relationshipx}`} />
                  </td>
                  <td>{dependent.relationshipOthers}</td>
                  <td>
                    {dependent.image ? (
                      <div>
                        {dependent.imageContentType ? (
                          <a onClick={openFile(dependent.imageContentType, dependent.image)}>
                            <img src={`data:${dependent.imageContentType};base64,${dependent.image}`} style={{ maxHeight: '30px' }} />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {dependent.imageContentType}, {byteSize(dependent.image)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{dependent.employee ? <Link to={`employee/${dependent.employee.id}`}>{dependent.employee.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${dependent.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${dependent.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${dependent.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="finGatewayApp.dependent.home.notFound">No Dependents found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ dependent }: IRootState) => ({
  dependentList: dependent.entities,
  loading: dependent.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Dependent);
