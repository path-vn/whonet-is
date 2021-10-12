export interface IBreakpoint {
  id?: number;
  guidelines?: string | null;
  year?: string | null;
  testMethod?: string | null;
  potency?: string | null;
  organismCode?: string | null;
  organismCodeType?: string | null;
  breakpointType?: string | null;
  host?: string | null;
  siteOfInfection?: string | null;
  referenceTable?: string | null;
  referenceSequence?: string | null;
  whonetAbxCode?: string | null;
  whonetTest?: string | null;
  r?: string | null;
  i?: string | null;
  sdd?: string | null;
  s?: string | null;
  ecvEcoff?: string | null;
  dateEntered?: string | null;
  dateModified?: string | null;
  comments?: string | null;
}

export const defaultValue: Readonly<IBreakpoint> = {};
