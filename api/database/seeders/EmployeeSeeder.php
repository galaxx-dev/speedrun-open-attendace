<?php

namespace Database\Seeders;

use App\Models\Employee;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;

class EmployeeSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        $data = [
            [
                "id"       => "10117124",
                "fullname" => "Iqbal Atma",
                "email"    => "iqbalatma@gmail.com",
                "phone"    => "0895351172040",
                "address"  => "Selakau",
                "gender"   => "male",
            ],
            [
                "id"       => "10016237",
                "fullname" => "Dio Ilham",
                "email"    => "dio@moontrack.net",
                "address"  => "Bekasi",
                "gender"   => "male",
            ]
        ];

        foreach ($data as $key => $value) {
            Employee::create($value);
        }
    }
}
