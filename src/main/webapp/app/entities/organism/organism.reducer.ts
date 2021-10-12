import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IOrganism, defaultValue } from 'app/shared/model/organism.model';

export const ACTION_TYPES = {
  FETCH_ORGANISM_LIST: 'organism/FETCH_ORGANISM_LIST',
  FETCH_ORGANISM: 'organism/FETCH_ORGANISM',
  CREATE_ORGANISM: 'organism/CREATE_ORGANISM',
  UPDATE_ORGANISM: 'organism/UPDATE_ORGANISM',
  PARTIAL_UPDATE_ORGANISM: 'organism/PARTIAL_UPDATE_ORGANISM',
  DELETE_ORGANISM: 'organism/DELETE_ORGANISM',
  RESET: 'organism/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IOrganism>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type OrganismState = Readonly<typeof initialState>;

// Reducer

export default (state: OrganismState = initialState, action): OrganismState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ORGANISM_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ORGANISM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ORGANISM):
    case REQUEST(ACTION_TYPES.UPDATE_ORGANISM):
    case REQUEST(ACTION_TYPES.DELETE_ORGANISM):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_ORGANISM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ORGANISM_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ORGANISM):
    case FAILURE(ACTION_TYPES.CREATE_ORGANISM):
    case FAILURE(ACTION_TYPES.UPDATE_ORGANISM):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_ORGANISM):
    case FAILURE(ACTION_TYPES.DELETE_ORGANISM):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ORGANISM_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_ORGANISM):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ORGANISM):
    case SUCCESS(ACTION_TYPES.UPDATE_ORGANISM):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_ORGANISM):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ORGANISM):
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

const apiUrl = 'api/organisms';

// Actions

export const getEntities: ICrudGetAllAction<IOrganism> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ORGANISM_LIST,
    payload: axios.get<IOrganism>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IOrganism> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ORGANISM,
    payload: axios.get<IOrganism>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IOrganism> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ORGANISM,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IOrganism> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ORGANISM,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IOrganism> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_ORGANISM,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IOrganism> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ORGANISM,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
