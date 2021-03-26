import { EntityMetadataMap, DefaultDataServiceConfig } from '@ngrx/data';
import { environment } from '../environments/environment';

const entityMetadata: EntityMetadataMap = {
  Todo: {},
};

export const entityConfig = {
  entityMetadata,
};

export const defaultDataServiceConfig: DefaultDataServiceConfig = {
  root: environment.apiEndpoint,
};
