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
    state: number;
    deprecated: boolean;
}

interface OrderInfo extends Order
{
    //TODO add more fields
}


export type {
    OrderCreate,
    Order,
    OrderInfo
}