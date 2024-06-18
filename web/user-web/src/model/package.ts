interface Package
{
    id: number;
    name: string;
    type: number;
    price: number;
    checkItems: CheckItem[];
}

interface CheckItem
{
    id: number;
    name: string;
    content: string;
    meaning: string;
    remarks: string;
}

export type { Package, CheckItem }