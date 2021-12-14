import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity, empty, merge } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAntibiotic, defaultValue } from 'app/shared/model/antibiotic.model';
import { ICrudGetAllActionWithFilter } from 'app/shared/util/filter';

export const ACTION_TYPES = {
  FETCH_ANTIBIOTIC_LIST: 'antibiotic/FETCH_ANTIBIOTIC_LIST',
  FETCH_ANTIBIOTIC: 'antibiotic/FETCH_ANTIBIOTIC',
  CREATE_ANTIBIOTIC: 'antibiotic/CREATE_ANTIBIOTIC',
  FILTER_BREAKPOINT: 'antibiotic/FILTER_ANTIBIOTIC',
  UPDATE_ANTIBIOTIC: 'antibiotic/UPDATE_ANTIBIOTIC',
  PARTIAL_UPDATE_ANTIBIOTIC: 'antibiotic/PARTIAL_UPDATE_ANTIBIOTIC',
  DELETE_ANTIBIOTIC: 'antibiotic/DELETE_ANTIBIOTIC',
  RESET: 'antibiotic/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAntibiotic>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
  filter: {},
};

export type AntibioticState = Readonly<typeof initialState>;

// Reducer

export default (state: AntibioticState = initialState, action): AntibioticState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FILTER_BREAKPOINT):
      return {
        ...state,
      };
    case FAILURE(ACTION_TYPES.FILTER_BREAKPOINT):
      return {
        ...state,
      };
    case SUCCESS(ACTION_TYPES.FILTER_BREAKPOINT):
      return {
        ...state,
        filter: merge(action.payload.data, state.filter),
      };
    case REQUEST(ACTION_TYPES.FETCH_ANTIBIOTIC_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ANTIBIOTIC):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ANTIBIOTIC):
    case REQUEST(ACTION_TYPES.UPDATE_ANTIBIOTIC):
    case REQUEST(ACTION_TYPES.DELETE_ANTIBIOTIC):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_ANTIBIOTIC):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ANTIBIOTIC_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ANTIBIOTIC):
    case FAILURE(ACTION_TYPES.CREATE_ANTIBIOTIC):
    case FAILURE(ACTION_TYPES.UPDATE_ANTIBIOTIC):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_ANTIBIOTIC):
    case FAILURE(ACTION_TYPES.DELETE_ANTIBIOTIC):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ANTIBIOTIC_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_ANTIBIOTIC):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ANTIBIOTIC):
    case SUCCESS(ACTION_TYPES.UPDATE_ANTIBIOTIC):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_ANTIBIOTIC):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ANTIBIOTIC):
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

const apiUrl = 'api/antibiotics';

// Actions

export const getEntities: ICrudGetAllActionWithFilter<IAntibiotic> = (page, size, sort, filter) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  const filters = empty(filter)
    ? []
    : Object.keys(filter).map(key => {
        return empty(filter[key]) || filter[key].length === 0 ? '' : `&${key}.in=${filter[key].map(k => k.value).join(',')}`;
      });
  return {
    type: ACTION_TYPES.FETCH_ANTIBIOTIC_LIST,
    payload: axios.get<IAntibiotic>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}${filters.join('')}`),
  };
};

export const getEntity: ICrudGetAction<IAntibiotic> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ANTIBIOTIC,
    payload: axios.get<IAntibiotic>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IAntibiotic> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ANTIBIOTIC,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAntibiotic> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ANTIBIOTIC,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IAntibiotic> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_ANTIBIOTIC,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAntibiotic> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ANTIBIOTIC,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});

export const getFilerGroup: ICrudGetAction<any> = key => {
  const requestUrl = `${apiUrl}/groups/${key}`;
  return {
    type: ACTION_TYPES.FILTER_BREAKPOINT,
    payload: axios.get<any>(requestUrl),
  };
};
