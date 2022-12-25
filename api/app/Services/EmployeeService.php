<?php
namespace App\Services;

use App\Models\Employee;
use App\Repositories\AccountRepository;
use App\Repositories\EmployeeRepository;
use Exception;
use Illuminate\Support\Facades\DB;

class EmployeeService
{
    public function getAllData()
    {
        return (new EmployeeRepository())->getAllDataPaginated();
    }

    public function getDataById(string $id): ? Employee
    {
        return (new EmployeeRepository())->getDataEmployeeById($id);
    }

    public function updateDataById(string $id, array $requestedData): int
    {
        return (new EmployeeRepository())->updateDataEmployeeById($id, $requestedData);
    }

    public function deleteDataById(string $id)
    {
        try {
            DB::beginTransaction();
            (new EmployeeRepository())->deleteDataById($id);
            (new AccountRepository())->suspendAccountById($id);
            DB::commit();
        } catch (Exception $error) {
            DB::rollBack();
            return false;
        }
        return true;
    }
}

?>
