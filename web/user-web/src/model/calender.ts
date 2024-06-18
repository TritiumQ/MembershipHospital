interface Calender 
{
    generateDate: string;
    appointmentCounts: AppointmentCount[];
}

interface AppointmentCount
{
    date: string;
    dayOfWeek: number;
    count: number;
}

export type
{
    Calender,
    AppointmentCount
}