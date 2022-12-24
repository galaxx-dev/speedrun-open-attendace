<?php
namespace App\Services;

use Illuminate\Http\JsonResponse;
use Illuminate\Support\Facades\Auth;

class AuthenticationService
{
    public function authenticate(array $requestedData): array
    {
        $token = Auth::attempt($requestedData);

        if (!$token) {
            return [
                "status"      => "error",
                "status_code" => JsonResponse::HTTP_UNAUTHORIZED,
                "message"     => "Unauthorized",
            ];
        }

        $user = Auth::user()->load("employee");
        return [
            "status"      => "success",
            "status_code" => JsonResponse::HTTP_OK,
            "message"     => "Login successfully",
            "payload"     => [
                "user"  => $user,
                "token" => $token,
                "type"  => "bearer"
            ]
        ];
    }
}

?>
