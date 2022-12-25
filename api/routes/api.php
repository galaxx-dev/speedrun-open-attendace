<?php

use App\Http\Controllers\API\v1\Auth\AuthController;
use App\Http\Controllers\API\v1\EmployeeController;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:sanctum')->get('/user', function (Request $request) {
    return $request->user();
});

Route::middleware("auth:api")->group(function () {
    Route::get(
        "/tes",
        function () {
            return response()->json(["tes" => "tes cok"]);
        }
    );
});


Route::controller(AuthController::class)
    ->name("auth.")
    ->prefix("/auth")
    ->group(function () {
        Route::post("/", "authenticate")->name("authenticate");
        Route::post("/logout", "logout")->name("logout");
        Route::post("/registration", "registration")->name("registration");
    });


Route::controller(EmployeeController::class)
    ->name("employees.")
    ->prefix("/employees")
    ->group(function () {
        Route::get("/", "index")->name("index");
        Route::get("/{id}", "show")->name("show");
        Route::put("/{id}", "update")->name("update");
        Route::delete("/{id}", "destroy")->name("destroy");
    });
