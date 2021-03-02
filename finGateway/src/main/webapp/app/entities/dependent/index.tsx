import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Dependent from './dependent';
import DependentDetail from './dependent-detail';
import DependentUpdate from './dependent-update';
import DependentDeleteDialog from './dependent-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DependentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DependentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DependentDetail} />
      <ErrorBoundaryRoute path={match.url} component={Dependent} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={DependentDeleteDialog} />
  </>
);

export default Routes;
