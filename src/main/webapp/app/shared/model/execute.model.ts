import dayjs from 'dayjs';

export interface IExecute {
  id?: number;
  request?: string | null;
  response?: string | null;
  startedAt?: string | null;
  time?: number | null;
}

export const defaultValue: Readonly<IExecute> = {};
