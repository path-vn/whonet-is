export interface IIntrinsicResistance {
  id?: number;
  abxCodeTypeguideline?: string | null;
  referenceTable?: string | null;
  organismCode?: string | null;
  organismCodeType?: string | null;
  exceptionOrganismCode?: string | null;
  exceptionOrganismCodeType?: string | null;
  abxCode?: string | null;
  abxCodeType?: string | null;
  antibioticExceptions?: string | null;
  dateEntered?: string | null;
  dateModified?: string | null;
  comments?: string | null;
}

export const defaultValue: Readonly<IIntrinsicResistance> = {};
