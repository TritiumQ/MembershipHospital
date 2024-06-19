import type { Hospital } from "./hospital";
import type { Package } from "./package";
import type { UserInfo } from "./user";

interface OrderCreate
{
    date?: string;
    userId?: string;
    hospitalId?: number;
    packageId?: number;
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
}

interface OrderInfo extends Order
{
    //TODO add more fields
    hospital: Hospital;
    user: UserInfo;
    packageField: Package;
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
}


export type {
    OrderCreate,
    Order,
    OrderInfo,
    OrderQuery
}