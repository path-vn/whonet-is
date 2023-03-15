import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IWhonetResource, defaultValue } from 'app/shared/model/whonet-resource.model';

export const ACTION_TYPES = {
  FETCH_WHONETRESOURCE_LIST: 'whonetResource/FETCH_WHONETRESOURCE_LIST',
  FETCH_WHONETRESOURCE: 'whonetResource/FETCH_WHONETRESOURCE',
  CREATE_WHONETRESOURCE: 'whonetResource/CREATE_WHONETRESOURCE',
  UPDATE_WHONETRESOURCE: 'whonetResource/UPDATE_WHONETRESOURCE',
  PARTIAL_UPDATE_WHONETRESOURCE: 'whonetResource/PARTIAL_UPDATE_WHONETRESOURCE',
  DELETE_WHONETRESOURCE: 'whonetResource/DELETE_WHONETRESOURCE',
  SET_BLOB: 'whonetResource/SET_BLOB',
  RESET: 'whonetResource/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IWhonetResource>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type WhonetResourceState = Readonly<typeof initialState>;

// Reducer

export default (state: WhonetResourceState = initialState, action): WhonetResourceState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_WHONETRESOURCE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_WHONETRESOURCE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_WHONETRESOURCE):
    case REQUEST(ACTION_TYPES.UPDATE_WHONETRESOURCE):
    case REQUEST(ACTION_TYPES.DELETE_WHONETRESOURCE):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_WHONETRESOURCE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_WHONETRESOURCE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_WHONETRESOURCE):
    case FAILURE(ACTION_TYPES.CREATE_WHONETRESOURCE):
    case FAILURE(ACTION_TYPES.UPDATE_WHONETRESOURCE):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_WHONETRESOURCE):
    case FAILURE(ACTION_TYPES.DELETE_WHONETRESOURCE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_WHONETRESOURCE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_WHONETRESOURCE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_WHONETRESOURCE):
    case SUCCESS(ACTION_TYPES.UPDATE_WHONETRESOURCE):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_WHONETRESOURCE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_WHONETRESOURCE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.SET_BLOB: {
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType,
        },
      };
    }
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/whonet-resources';

// Actions

export const getEntities: ICrudGetAllAction<IWhonetResource> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_WHONETRESOURCE_LIST,
    payload: axios.get<IWhonetResource>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IWhonetResource> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_WHONETRESOURCE,
    payload: axios.get<IWhonetResource>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IWhonetResource> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_WHONETRESOURCE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IWhonetResource> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_WHONETRESOURCE,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IWhonetResource> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_WHONETRESOURCE,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IWhonetResource> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_WHONETRESOURCE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType,
  },
});

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
