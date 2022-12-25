<?php
namespace App\Repositories;

use App\Models\Account;
use App\Models\Employee;
use Illuminate\Pagination\LengthAwarePaginator;

class EmployeeRepository
{

    protected Employee $model;
    public function __construct()
    {
        $this->model = new Employee();
    }


    public function addNewDataEmployee(array $requestedData): Employee
    {
        return $this->model->create($requestedData);
    }

    public function getDataEmployeeById(string $id, array $columns = ["*"]): ? Employee
    {
        return $this->model->select($columns)->find($id);
    }

    public function getAllDataPaginated(array $columns = ["*"]): ? LengthAwarePaginator
    {
        return $this->model->paginate();
    }

    public function updateDataEmployeeById(string $id, array $requestedData): int
    {
        return $this->model->where("id", $id)->update($requestedData);
    }
}

?>
