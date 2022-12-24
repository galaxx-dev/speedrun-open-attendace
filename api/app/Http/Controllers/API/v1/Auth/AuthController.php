<?php

namespace App\Http\Controllers\API\v1\Auth;

use App\Http\Controllers\Controller;
use App\Http\Requests\Auth\AuthenticationRequest;
use App\Services\AuthenticationService;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;
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
            'tes'         => $tes
        ]);
    }


    public function registration(AuthenticationService $service)
    {
    }
}
