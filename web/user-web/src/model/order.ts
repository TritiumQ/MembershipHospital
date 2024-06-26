import type { Family } from "./family";
import type { Hospital } from "./hospital";
import type { Package } from "./package";
import type { UserInfo } from "./user";

interface OrderCreate
{
    date?: string;
    userId?: string;
    hospitalId?: number;
    packageId?: number;
    familyId?: number;
}

interface Order
{
    id: number;
    date: string;
    userId: string;
    hospitalId: string;
    packageId: string;
    packageName: string;
    state: number;
    deprecated: boolean;
    pay: number;
    familyId?: number;
}

interface OrderInfo extends Order
{
    hospital: Hospital;
    user: UserInfo;
    packageField: Package;
    family?: Family;
}

interface OrderQuery
{
    startDate?: string;
    endDate?: string;
    userId?: string;
    hospitalId?: number;
    packageId?: number;
    state?: number;
    deprecated?: boolean;
    familyId?: number;
}


export type {
    OrderCreate,
    Order,
    OrderInfo,
    OrderQuery
}