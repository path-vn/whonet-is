import dayjs from 'dayjs';

export interface IWhonetResource {
  id?: number;
  uploadDate?: string | null;
  antibiotic?: string | null;
  organism?: string | null;
  intrinsicResistance?: string | null;
  expertRule?: string | null;
  breakPoint?: string | null;
  status?: string | null;
  importedDate?: string | null;
  message?: string | null;
}

export const defaultValue: Readonly<IWhonetResource> = {};
