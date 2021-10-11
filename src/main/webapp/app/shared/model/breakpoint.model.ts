export interface IBreakpoint {
  id?: number;
  path?: string | null;
  query?: string | null;
  antibioticQuery?: string | null;
  organismQuery?: string | null;
  intrinsicResistanceQuery?: string | null;
  binaryDataContentType?: string | null;
  binaryData?: string | null;
}

export const defaultValue: Readonly<IBreakpoint> = {};
