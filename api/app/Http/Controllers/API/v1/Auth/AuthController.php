<?php

namespace App\Http\Controllers\API\v1\Auth;

use App\Http\Controllers\Controller;
use App\Http\Requests\Auth\AuthenticationRequest;
use App\Http\Requests\Auth\RegistrationRequest;
use App\Services\AuthenticationService;
use App\Services\RegistrationService;
use Illuminate\Http\JsonResponse;
use Illuminate\Support\Facades\Auth;

class AuthController extends Controller
{
    public function authenticate(AuthenticationService $service, AuthenticationRequest $request): JsonResponse
    {
        $authenticated = $service->authenticate($request->validated());

        if ($authenticated["status"] == "error") {
            return response()->json($authenticated, JsonResponse::HTTP_UNAUTHORIZED);
        }
        return response()->json($authenticated, JsonResponse::HTTP_OK);
    }

    public function logout()
    {
        $tes = Auth::logout();
        return response()->json([
            'status'      => 'success',
            'status_code' => JsonResponse::HTTP_OK,
            'message'     => 'Successfully logged out',
        ]);
    }


    public function registration(RegistrationService $service, RegistrationRequest $request)
    {
        $stored = $service->registration($request->validated());
        if ($stored) {
            return response()->json([
                "status"      => "success",
                "status_code" => JsonResponse::HTTP_CREATED,
                "message"     => "Registration new user successfully",
                "payload"     => $stored
            ]);
        }

        return response()->json([
            'status'      => 'error',
            'status_code' => JsonResponse::HTTP_NOT_ACCEPTABLE,
            'message'     => 'Something went wrong',
        ]);
    }
}
