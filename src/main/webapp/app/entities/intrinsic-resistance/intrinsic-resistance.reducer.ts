import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity, empty, merge } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IIntrinsicResistance, defaultValue } from 'app/shared/model/intrinsic-resistance.model';
import { ICrudGetAllActionWithFilter } from 'app/shared/util/filter';

export const ACTION_TYPES = {
  FETCH_INTRINSICRESISTANCE_LIST: 'intrinsicResistance/FETCH_INTRINSICRESISTANCE_LIST',
  FETCH_INTRINSICRESISTANCE: 'intrinsicResistance/FETCH_INTRINSICRESISTANCE',
  CREATE_INTRINSICRESISTANCE: 'intrinsicResistance/CREATE_INTRINSICRESISTANCE',
  FILTER_BREAKPOINT: 'intrinsicResistance/FILTER_INTRINSICRESISTANCE',
  UPDATE_INTRINSICRESISTANCE: 'intrinsicResistance/UPDATE_INTRINSICRESISTANCE',
  PARTIAL_UPDATE_INTRINSICRESISTANCE: 'intrinsicResistance/PARTIAL_UPDATE_INTRINSICRESISTANCE',
  DELETE_INTRINSICRESISTANCE: 'intrinsicResistance/DELETE_INTRINSICRESISTANCE',
  RESET: 'intrinsicResistance/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IIntrinsicResistance>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
  filter: {},
};

export type IntrinsicResistanceState = Readonly<typeof initialState>;

// Reducer

export default (state: IntrinsicResistanceState = initialState, action): IntrinsicResistanceState => {
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
    case REQUEST(ACTION_TYPES.FETCH_INTRINSICRESISTANCE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_INTRINSICRESISTANCE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_INTRINSICRESISTANCE):
    case REQUEST(ACTION_TYPES.UPDATE_INTRINSICRESISTANCE):
    case REQUEST(ACTION_TYPES.DELETE_INTRINSICRESISTANCE):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_INTRINSICRESISTANCE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_INTRINSICRESISTANCE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_INTRINSICRESISTANCE):
    case FAILURE(ACTION_TYPES.CREATE_INTRINSICRESISTANCE):
    case FAILURE(ACTION_TYPES.UPDATE_INTRINSICRESISTANCE):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_INTRINSICRESISTANCE):
    case FAILURE(ACTION_TYPES.DELETE_INTRINSICRESISTANCE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_INTRINSICRESISTANCE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_INTRINSICRESISTANCE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_INTRINSICRESISTANCE):
    case SUCCESS(ACTION_TYPES.UPDATE_INTRINSICRESISTANCE):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_INTRINSICRESISTANCE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_INTRINSICRESISTANCE):
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

const apiUrl = 'api/intrinsic-resistances';

// Actions
export const getEntities: ICrudGetAllActionWithFilter<IIntrinsicResistance> = (page, size, sort, filter) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  const filters = empty(filter)
    ? []
    : Object.keys(filter).map(key => {
        return empty(filter[key]) || filter[key].length === 0 ? '' : `&${key}.in=${filter[key].map(k => k.value).join(',')}`;
      });
  return {
    type: ACTION_TYPES.FETCH_INTRINSICRESISTANCE_LIST,
    payload: axios.get<IIntrinsicResistance>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}${filters}`),
  };
};

export const getEntity: ICrudGetAction<IIntrinsicResistance> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_INTRINSICRESISTANCE,
    payload: axios.get<IIntrinsicResistance>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IIntrinsicResistance> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_INTRINSICRESISTANCE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IIntrinsicResistance> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_INTRINSICRESISTANCE,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IIntrinsicResistance> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_INTRINSICRESISTANCE,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IIntrinsicResistance> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_INTRINSICRESISTANCE,
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
