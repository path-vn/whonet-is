export interface IExpertInterpretationRules {
  id?: number;
  ruleCode?: string | null;
  description?: string | null;
  organismCode?: string | null;
  organismCodeType?: string | null;
  ruleCriteria?: string | null;
  affectedAntibiotics?: string | null;
  antibioticExceptions?: string | null;
  result?: string | null;
}

export const defaultValue: Readonly<IExpertInterpretationRules> = {};
