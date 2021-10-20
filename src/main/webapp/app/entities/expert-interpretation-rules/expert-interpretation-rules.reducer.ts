import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IExpertInterpretationRules, defaultValue } from 'app/shared/model/expert-interpretation-rules.model';

export const ACTION_TYPES = {
  FETCH_EXPERTINTERPRETATIONRULES_LIST: 'expertInterpretationRules/FETCH_EXPERTINTERPRETATIONRULES_LIST',
  FETCH_EXPERTINTERPRETATIONRULES: 'expertInterpretationRules/FETCH_EXPERTINTERPRETATIONRULES',
  CREATE_EXPERTINTERPRETATIONRULES: 'expertInterpretationRules/CREATE_EXPERTINTERPRETATIONRULES',
  UPDATE_EXPERTINTERPRETATIONRULES: 'expertInterpretationRules/UPDATE_EXPERTINTERPRETATIONRULES',
  PARTIAL_UPDATE_EXPERTINTERPRETATIONRULES: 'expertInterpretationRules/PARTIAL_UPDATE_EXPERTINTERPRETATIONRULES',
  DELETE_EXPERTINTERPRETATIONRULES: 'expertInterpretationRules/DELETE_EXPERTINTERPRETATIONRULES',
  RESET: 'expertInterpretationRules/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IExpertInterpretationRules>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ExpertInterpretationRulesState = Readonly<typeof initialState>;

// Reducer

export default (state: ExpertInterpretationRulesState = initialState, action): ExpertInterpretationRulesState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EXPERTINTERPRETATIONRULES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EXPERTINTERPRETATIONRULES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_EXPERTINTERPRETATIONRULES):
    case REQUEST(ACTION_TYPES.UPDATE_EXPERTINTERPRETATIONRULES):
    case REQUEST(ACTION_TYPES.DELETE_EXPERTINTERPRETATIONRULES):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_EXPERTINTERPRETATIONRULES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_EXPERTINTERPRETATIONRULES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EXPERTINTERPRETATIONRULES):
    case FAILURE(ACTION_TYPES.CREATE_EXPERTINTERPRETATIONRULES):
    case FAILURE(ACTION_TYPES.UPDATE_EXPERTINTERPRETATIONRULES):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_EXPERTINTERPRETATIONRULES):
    case FAILURE(ACTION_TYPES.DELETE_EXPERTINTERPRETATIONRULES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_EXPERTINTERPRETATIONRULES_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_EXPERTINTERPRETATIONRULES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_EXPERTINTERPRETATIONRULES):
    case SUCCESS(ACTION_TYPES.UPDATE_EXPERTINTERPRETATIONRULES):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_EXPERTINTERPRETATIONRULES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_EXPERTINTERPRETATIONRULES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/expert-interpretation-rules';

// Actions

export const getEntities: ICrudGetAllAction<IExpertInterpretationRules> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_EXPERTINTERPRETATIONRULES_LIST,
    payload: axios.get<IExpertInterpretationRules>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IExpertInterpretationRules> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EXPERTINTERPRETATIONRULES,
    payload: axios.get<IExpertInterpretationRules>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IExpertInterpretationRules> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EXPERTINTERPRETATIONRULES,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IExpertInterpretationRules> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EXPERTINTERPRETATIONRULES,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IExpertInterpretationRules> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_EXPERTINTERPRETATIONRULES,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IExpertInterpretationRules> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EXPERTINTERPRETATIONRULES,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
