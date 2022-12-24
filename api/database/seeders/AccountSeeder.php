<?php

namespace Database\Seeders;

use App\Models\Account;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\Hash;

class AccountSeeder extends Seeder
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
                "employee_id" => "10117124",
                "password"    => Hash::make("admin"),
                "is_active"   => 1,
            ],
            [
                "employee_id" => "10016237",
                "password"    => Hash::make("admin"),
                "is_active"   => 1,
            ],
            [
                "employee_id" => "10117126",
                "password"    => Hash::make("admin"),
                "is_active"   => 1,
            ],
        ];

        foreach ($data as $key => $value) {
            Account::create($value);
        }
    }
}
