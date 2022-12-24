<?php

namespace App\Http\Requests\Auth;

use Illuminate\Foundation\Http\FormRequest;

class RegistrationRequest extends FormRequest
{
    /**
     * Determine if the user is authorized to make this request.
     *
     * @return bool
     */
    public function authorize()
    {
        return true;
    }

    /**
     * Get the validation rules that apply to the request.
     *
     * @return array<string, mixed>
     */
    public function rules()
    {
        return [
            "employee_id" => "required|numeric|digits_between:1,16",
            "fullname"    => "max:128",
            "email"       => "email|max:128",
            "phone"       => "max:32",
            "address"     => "max:256",
            "gender"      => "required|in:male,female",
            "password"    => "required|min:4|max:128"
        ];
    }
}
