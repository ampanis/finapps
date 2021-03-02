import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IDependent, defaultValue } from 'app/shared/model/dependent.model';

export const ACTION_TYPES = {
  FETCH_DEPENDENT_LIST: 'dependent/FETCH_DEPENDENT_LIST',
  FETCH_DEPENDENT: 'dependent/FETCH_DEPENDENT',
  CREATE_DEPENDENT: 'dependent/CREATE_DEPENDENT',
  UPDATE_DEPENDENT: 'dependent/UPDATE_DEPENDENT',
  DELETE_DEPENDENT: 'dependent/DELETE_DEPENDENT',
  SET_BLOB: 'dependent/SET_BLOB',
  RESET: 'dependent/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IDependent>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type DependentState = Readonly<typeof initialState>;

// Reducer

export default (state: DependentState = initialState, action): DependentState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_DEPENDENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_DEPENDENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_DEPENDENT):
    case REQUEST(ACTION_TYPES.UPDATE_DEPENDENT):
    case REQUEST(ACTION_TYPES.DELETE_DEPENDENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_DEPENDENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_DEPENDENT):
    case FAILURE(ACTION_TYPES.CREATE_DEPENDENT):
    case FAILURE(ACTION_TYPES.UPDATE_DEPENDENT):
    case FAILURE(ACTION_TYPES.DELETE_DEPENDENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_DEPENDENT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_DEPENDENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_DEPENDENT):
    case SUCCESS(ACTION_TYPES.UPDATE_DEPENDENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_DEPENDENT):
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

const apiUrl = 'api/dependents';

// Actions

export const getEntities: ICrudGetAllAction<IDependent> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_DEPENDENT_LIST,
  payload: axios.get<IDependent>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IDependent> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_DEPENDENT,
    payload: axios.get<IDependent>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IDependent> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_DEPENDENT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IDependent> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_DEPENDENT,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IDependent> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_DEPENDENT,
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
