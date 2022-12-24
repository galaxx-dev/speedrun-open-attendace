<?php
namespace App\Services;

use App\Models\Employee;
use App\Repositories\AccountRepository;
use App\Repositories\EmployeeRepository;
use Exception;
use Illuminate\Http\JsonResponse;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Hash;
use PhpParser\Node\Expr\Array_;


/**
 *
 * * NOTES KIRIM EMAIL KE USER BARU BELUM
 */
class RegistrationService
{
    public function registration(array $requestedData): bool|Employee
    {
        $requestedData["password"] = Hash::make($requestedData["password"]);
        $requestedData["id"] = $requestedData["employee_id"];
        $datasetCollection = collect($requestedData);
        try {
            DB::beginTransaction();
            (new AccountRepository())
                ->addNewDataAccount(
                    $datasetCollection->only("employee_id", "password")->toArray()
                );

            $dataEmployee = (new EmployeeRepository())
                ->addNewDataEmployee(
                    $datasetCollection->except("employee_id", "password")->toArray()
                );

            DB::commit();
        } catch (Exception $e) {
            DB::rollBack();
            return $e->getMessage();
        }

        return $dataEmployee;
    }
}

?>
