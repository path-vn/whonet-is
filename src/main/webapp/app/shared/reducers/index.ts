import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import breakpoint, {
  BreakpointState
} from 'app/entities/breakpoint/breakpoint.reducer';
// prettier-ignore
import intrinsicResistance, {
  IntrinsicResistanceState
} from 'app/entities/intrinsic-resistance/intrinsic-resistance.reducer';
// prettier-ignore
import antibiotic, {
  AntibioticState
} from 'app/entities/antibiotic/antibiotic.reducer';
// prettier-ignore
import organism, {
  OrganismState
} from 'app/entities/organism/organism.reducer';
// prettier-ignore
import expertInterpretationRules, {
  ExpertInterpretationRulesState
} from 'app/entities/expert-interpretation-rules/expert-interpretation-rules.reducer';
// prettier-ignore
import execute, {
  ExecuteState
} from 'app/entities/execute/execute.reducer';
// prettier-ignore
import whonetResource, {
  WhonetResourceState
} from 'app/entities/whonet-resource/whonet-resource.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly breakpoint: BreakpointState;
  readonly intrinsicResistance: IntrinsicResistanceState;
  readonly antibiotic: AntibioticState;
  readonly organism: OrganismState;
  readonly expertInterpretationRules: ExpertInterpretationRulesState;
  readonly execute: ExecuteState;
  readonly whonetResource: WhonetResourceState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  breakpoint,
  intrinsicResistance,
  antibiotic,
  organism,
  expertInterpretationRules,
  execute,
  whonetResource,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
});

export default rootReducer;
