<?php

namespace App\Http\Controllers\API\v1;

use App\Http\Controllers\Controller;
use App\Http\Requests\Employees\UpdateEmployeeRequest;
use App\Http\Resources\Employees\EmployeeResource;
use App\Http\Resources\Employees\EmployeeResourceCollection;
use App\Services\EmployeeService;
use Illuminate\Http\JsonResponse;

class EmployeeController extends Controller
{
    public function index(EmployeeService $service)
    {
        $data = $service->getAllData();
        return (new EmployeeResourceCollection($data))->additional([
            "status"      => "success",
            "status_code" => JsonResponse::HTTP_OK,
            "message"     => "Get employee successfully"
        ]);
    }

    public function show(EmployeeService $service, string $id)
    {
        $data = $service->getDataById($id);
        return (new EmployeeResource($data))->additional([
            "status"      => "success",
            "status_code" => JsonResponse::HTTP_OK,
            "message"     => "Get employee by id successfully"
        ]);
    }
    public function update(EmployeeService $service, UpdateEmployeeRequest $request, string $id)
    {
        $updated = $service->updateDataById($id, $request->validated());
        if ($updated) {
            return response()->json([
                "status"      => "success",
                "status_code" => JsonResponse::HTTP_OK,
                "message"     => "Update employee by id successfully"
            ]);
        }

        return response()->json([
            "status"      => "error",
            "status_code" => JsonResponse::HTTP_NOT_ACCEPTABLE,
            "message"     => "Update employee failed. Something went wrong"
        ]);
    }
}
