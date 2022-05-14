import axios from 'axios';
import { ICrudDeleteAction, ICrudGetAction, ICrudPutAction } from 'react-jhipster';

import { cleanEntity, empty, merge } from 'app/shared/util/entity-utils';
import { FAILURE, REQUEST, SUCCESS } from 'app/shared/reducers/action-type.util';

import { defaultValue, IBreakpoint } from 'app/shared/model/breakpoint.model';
import { ICrudGetAllActionWithFilter } from 'app/shared/util/filter';

export const ACTION_TYPES = {
  FETCH_BREAKPOINT_LIST: 'breakpoint/FETCH_BREAKPOINT_LIST',
  FETCH_BREAKPOINT: 'breakpoint/FETCH_BREAKPOINT',
  CREATE_BREAKPOINT: 'breakpoint/CREATE_BREAKPOINT',
  UPDATE_BREAKPOINT: 'breakpoint/UPDATE_BREAKPOINT',
  PARTIAL_UPDATE_BREAKPOINT: 'breakpoint/PARTIAL_UPDATE_BREAKPOINT',
  DELETE_BREAKPOINT: 'breakpoint/DELETE_BREAKPOINT',
  FILTER_BREAKPOINT: 'breakpoint/FILTER_BREAKPOINT',
  RESET: 'breakpoint/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBreakpoint>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
  filter: {},
};

export type BreakpointState = Readonly<typeof initialState>;

// Reducer

export default (state: BreakpointState = initialState, action): BreakpointState => {
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
    case REQUEST(ACTION_TYPES.FETCH_BREAKPOINT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BREAKPOINT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_BREAKPOINT):
    case REQUEST(ACTION_TYPES.UPDATE_BREAKPOINT):
    case REQUEST(ACTION_TYPES.DELETE_BREAKPOINT):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_BREAKPOINT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_BREAKPOINT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BREAKPOINT):
    case FAILURE(ACTION_TYPES.CREATE_BREAKPOINT):
    case FAILURE(ACTION_TYPES.UPDATE_BREAKPOINT):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_BREAKPOINT):
    case FAILURE(ACTION_TYPES.DELETE_BREAKPOINT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_BREAKPOINT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_BREAKPOINT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_BREAKPOINT):
    case SUCCESS(ACTION_TYPES.UPDATE_BREAKPOINT):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_BREAKPOINT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_BREAKPOINT):
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

const apiUrl = 'api/breakpoints';

// Actions

export const getEntities: ICrudGetAllActionWithFilter<IBreakpoint> = (page, size, sort, filter) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  const filters = empty(filter)
    ? []
    : Object.keys(filter).map(key => {
        return empty(filter[key]) || filter[key].length === 0 ? '' : `&${key}.in=${filter[key].map(k => k.value).join(',')}`;
      });
  return {
    type: ACTION_TYPES.FETCH_BREAKPOINT_LIST,
    payload: axios.get<IBreakpoint>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}${filters.join('')}`),
  };
};

export const getEntity: ICrudGetAction<IBreakpoint> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BREAKPOINT,
    payload: axios.get<IBreakpoint>(requestUrl),
  };
};

export const getFilerGroup: ICrudGetAction<any> = key => {
  const requestUrl = `${apiUrl}/groups/${key}`;
  return {
    type: ACTION_TYPES.FILTER_BREAKPOINT,
    payload: axios.get<any>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IBreakpoint> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BREAKPOINT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IBreakpoint> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BREAKPOINT,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IBreakpoint> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_BREAKPOINT,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBreakpoint> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BREAKPOINT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
