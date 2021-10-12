import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Breakpoint from './breakpoint';
import Antibiotic from './antibiotic';
import IntrinsicResistance from './intrinsic-resistance';
import Organism from './organism';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}breakpoint`} component={Breakpoint} />
      <ErrorBoundaryRoute path={`${match.url}antibiotic`} component={Antibiotic} />
      <ErrorBoundaryRoute path={`${match.url}intrinsic-resistance`} component={IntrinsicResistance} />
      <ErrorBoundaryRoute path={`${match.url}organism`} component={Organism} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
