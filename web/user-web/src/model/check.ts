import type { Hospital } from "./hospital";

interface CheckReport 
{
    orderId: number;
    date: string;
    hospital: Hospital;
    overallResults: OverallResult[];
    checkItemReports: CheckItemReport[];
}

interface OverallResult {
    id: number;
    title: string;
    content: string;
    orderId: number;
}

interface CheckItem {
    id: number;
    name: string;
    content: string;
    meaning: string;
    remarks: string;
}

interface CheckItemDetailed {
    id: number;
    name: string;
    unit?: string;
    minRange?: number;
    maxRange?: number;
    normalValue?: string;
    normalValueDescription?: string;
    type: number;
    remarks: string;
}

interface DetailedReport {
    id: number;
    value: string;
    error: number;
    orderId: number;
    checkItemReportId: number;
    checkItemDetailed: CheckItemDetailed;
}

interface CheckItemReport {
    id: number;
    review: string;
    orderId: number;
    checkItem: CheckItem;
    detailedReports: DetailedReport[];
}

export type { CheckReport, OverallResult, CheckItem, CheckItemDetailed, DetailedReport, CheckItemReport };