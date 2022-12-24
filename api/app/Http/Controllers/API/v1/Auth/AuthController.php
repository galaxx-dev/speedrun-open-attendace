<?php

namespace App\Http\Controllers\API\v1\Auth;

use App\Http\Controllers\Controller;
use App\Http\Requests\Auth\AuthenticationRequest;
use App\Services\AuthenticationService;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;

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
}
