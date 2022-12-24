<?php

namespace App\Models;

use Attribute;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Foundation\Auth\User as Authenticatable;
use Illuminate\Database\Eloquent\SoftDeletes;
use Illuminate\Support\Facades\Hash;
use PHPOpenSourceSaver\JWTAuth\Contracts\JWTSubject;

class Account extends Authenticatable implements JWTSubject
{
    use HasFactory, SoftDeletes;

    protected $fillable = ["employee_id", "password", "is_active"];
    protected $hidden = [
        'password',
    ];


    public function password(): Attribute
    {
        return Attribute::make(
        set: fn($value) => Hash::make($value),
        );
    }

    public function employee()
    {
        return $this->belongsTo(Employee::class);
    }

    public function getJWTIdentifier()
    {
        return $this->getKey();
    }

    public function getJWTCustomClaims()
    {
        return [
            "user" => [
                'employee_id' => $this->employee_id,
                'is_active'   => $this->is_active,
                'email'       => $this->employee->email,
            ]
        ];

    }
}
