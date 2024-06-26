interface Family
{
    id: number;
    userId: string;
    name: string;
    phone: string;
    birthday: string;
    idCard: string;
    sex: number;
}

interface FamilyCreate extends Omit<Family, 'id'> { }

export type { Family, FamilyCreate }